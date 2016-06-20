package com.dsi.authentication.resource;

import com.dsi.authentication.exception.CustomException;
import com.dsi.authentication.exception.ErrorContext;
import com.dsi.authentication.exception.ErrorMessage;
import com.dsi.authentication.model.Login;
import com.dsi.authentication.model.Tenant;
import com.dsi.authentication.model.UserSession;
import com.dsi.authentication.service.*;
import com.dsi.authentication.service.impl.*;
import com.dsi.authentication.util.Constants;
import com.dsi.authentication.util.Utility;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import io.jsonwebtoken.Claims;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sabbir on 6/16/16.
 */

@Path("/v1/login_session")
@Api(value = "/Authentication", description = "Operations about Authentication")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.WILDCARD})
public class LoginResource {

    private static final Logger logger = Logger.getLogger(LoginResource.class);

    private static final UserSessionService userSessionService = new UserSessionServiceImpl();
    private static final TenantService tenantService = new TenantServiceImpl();
    private static final LoginFactory loginFactory = new LoginFactoryImpl();
    private static final TokenService tokenService = new TokenServiceImpl();

    @Context
    HttpServletRequest request;

    @POST
    @ApiOperation(value = "Start Login Session", notes = "Start Login Session", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Login success"),
            @ApiResponse(code = 500, message = "Login failed, unauthorized.")
    })
    public Response startLoginSession(String requestBody) throws Exception {
        JSONObject responseObj = new JSONObject();
        JSONObject requestObj;

        ErrorContext errorContext;
        //try {
            logger.info("Request Body: " + requestBody);

            requestObj = new JSONObject(requestBody);
            String username = Utility.validation(requestObj, "username");
            String password = Utility.validation(requestObj, "password");
            String tenantID = Utility.validation(requestObj, "tenant_id");

            if(!Utility.isNullOrEmpty(username) && !Utility.isNullOrEmpty(password)
                    && !Utility.isNullOrEmpty(tenantID)){

                Tenant tenant = tenantService.getTenantByID(tenantID);
                if(tenant != null){
                    logger.info("Tenant active.");
                    String authHandlerClassName = tenant.getAuthHandler().getTypeImpl();
                    logger.info("Auth handler class name: " + authHandlerClassName);

                    LoginHandler loginHandler = (LoginHandler) loginFactory.getInstance(authHandlerClassName);
                    if(loginHandler != null) {

                        Login login = loginHandler.validateUser(username, password);
                        if (login != null) {
                            logger.info("Login successfully.");

                            responseObj.put("login_id", login.getLoginId());
                            responseObj.put("first_name", login.getFirstName());
                            responseObj.put("last_name", login.getLastName());
                            responseObj.put("user_id", login.getUserId());
                            responseObj.put("username", login.getEmail());

                            String accessToken = tokenService.createToken(login.getUserId(), login.getFirstName(),
                                    responseObj.toString(), Constants.TIME_INTERVAL);

                            logger.info("Generated AccessToken: " + accessToken);
                            responseObj.put("access_token", accessToken);

                            UserSession userSession = new UserSession();
                            userSession.setUserId(login.getUserId());
                            userSession.setCreateBy(login.getUserId());
                            userSession.setModifiedBy(login.getUserId());
                            userSession.setAccessToken(accessToken);
                            userSession.setCreatedDate(Utility.today());
                            userSession.setModifiedDate(Utility.today());
                            userSession.setVersion(1);
                            userSessionService.saveUserSession(userSession);
                            logger.info("User session save successfully.");

                            return Response.ok().entity(responseObj.toString()).build();

                        } else {
                            logger.error("Login failed.");
                            errorContext = new ErrorContext(null, null, Constants.AUTHENTICATE_SERVICE_0001_DESCRIPTION);
                            ErrorMessage errorMessage = new ErrorMessage(Constants.AUTHENTICATE_SERVICE_0001, Constants.AUTHENTICATE_SERVICE_0001_DESCRIPTION, errorContext);
                            throw new CustomException(errorMessage);
                        }
                    } else {
                        logger.error("Handler class not defined.");
                        errorContext = new ErrorContext(tenantID, "Tenant", "Handler class name not found of this tenantID: " + tenantID);
                        ErrorMessage errorMessage = new ErrorMessage(Constants.AUTHENTICATE_SERVICE_0002, Constants.AUTHENTICATE_SERVICE_0002_DESCRIPTION, errorContext);
                        throw new CustomException(errorMessage);
                    }
                } else {
                    logger.error("Tenant not found.");
                    errorContext = new ErrorContext(tenantID, "Tenant", "Tenant ID: " + tenantID + " not found.");
                    ErrorMessage errorMessage = new ErrorMessage(Constants.AUTHENTICATE_SERVICE_0003, Constants.AUTHENTICATE_SERVICE_0003_DESCRIPTION, errorContext);
                    throw new CustomException(errorMessage);
                }
            }
        /*} catch (Exception e){
            logger.error("Failed to start login session:: " + e.getMessage());
            errorContext = new ErrorContext(null, null, e.getMessage());
        }*/
        errorContext = new ErrorContext(null, null, "Exception occurs.");
        ErrorMessage errorMessage = new ErrorMessage(Constants.AUTHENTICATE_SERVICE_0005, Constants.AUTHENTICATE_SERVICE_0005_DESCRIPTION, errorContext);
        throw new CustomException(errorMessage);
        //return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObj.toString()).build();
    }

    @DELETE
    @ApiOperation(value = "Delete Login Session", notes = "Delete Login Session", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Logout success"),
            @ApiResponse(code = 500, message = "Logout failed, unauthorized.")
    })
    public Response deleteLoginSession() throws Exception {
        String accessToken = request.getAttribute("access_token") != null ?
                request.getAttribute("access_token").toString() : null;

        JSONObject responseObj = new JSONObject();

        ErrorContext errorContext;
        //try {
            if (!Utility.isNullOrEmpty(accessToken)) {

                Claims parseToken = tokenService.parseToken(accessToken);
                if(parseToken != null){

                    UserSession userSession = userSessionService.getUserSessionByUserIdAndAccessToken(parseToken.getId(), accessToken);
                    if(userSession != null){
                        userSessionService.deleteUserSession(userSession);
                        logger.info("Delete user session successfully.");

                        responseObj.put(Constants.MESSAGE, "Delete user session success");
                        return Response.ok().entity(responseObj.toString()).build();

                    } else {
                        logger.error("User session not found.");
                        errorContext = new ErrorContext(null, null, Constants.AUTHENTICATE_SERVICE_0006_DESCRIPTION);
                        ErrorMessage errorMessage = new ErrorMessage(Constants.AUTHENTICATE_SERVICE_0006, Constants.AUTHENTICATE_SERVICE_0006_DESCRIPTION, errorContext);
                        throw new CustomException(errorMessage);
                    }
                } else {
                    logger.error("Token parse failed.");
                    errorContext = new ErrorContext(null, null, "Token parse failed.");
                    ErrorMessage errorMessage = new ErrorMessage(Constants.AUTHENTICATE_SERVICE_0007, Constants.AUTHENTICATE_SERVICE_0007_DESCRIPTION, errorContext);
                    throw new CustomException(errorMessage);
                }
            }
        /*} catch (Exception e){
            logger.error("Failed to delete login session:: " + e.getMessage());
        }*/
        errorContext = new ErrorContext(null, null, "Exception occurs.");
        ErrorMessage errorMessage = new ErrorMessage(Constants.AUTHENTICATE_SERVICE_0005, Constants.AUTHENTICATE_SERVICE_0005_DESCRIPTION, errorContext);
        throw new CustomException(errorMessage);
        //return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObj.toString()).build();
    }

    @GET
    @ApiOperation(value = "Get Login Info", notes = "Get Login Info", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get login info"),
            @ApiResponse(code = 500, message = "Get login info failed, unauthorized.")
    })
    public Response getLoginSession() throws Exception {
        String accessToken = request.getAttribute("access_token") != null ?
                request.getAttribute("access_token").toString() : null;

        JSONObject responseObj = new JSONObject();

        ErrorContext errorContext;
        //try {
            if (!Utility.isNullOrEmpty(accessToken)) {

                Claims parseToken = tokenService.parseToken(accessToken);
                if (parseToken != null) {

                    UserSession userSession = userSessionService.getUserSessionByUserIdAndAccessToken(parseToken.getId(), accessToken);
                    if(userSession != null){

                        String subject = parseToken.getSubject();
                        logger.info("ParseToken Subject: " + subject);

                        responseObj = new JSONObject(subject);
                        logger.info("Login into: " + responseObj.toString());
                        return Response.ok().entity(responseObj.toString()).build();

                    } else {
                        logger.error("User session not found.");
                        errorContext = new ErrorContext(null, null, Constants.AUTHENTICATE_SERVICE_0006_DESCRIPTION);
                        ErrorMessage errorMessage = new ErrorMessage(Constants.AUTHENTICATE_SERVICE_0006, Constants.AUTHENTICATE_SERVICE_0006_DESCRIPTION, errorContext);
                        throw new CustomException(errorMessage);
                    }
                } else {
                    logger.error("Token parse failed.");
                    errorContext = new ErrorContext(null, null, "Token parse failed.");
                    ErrorMessage errorMessage = new ErrorMessage(Constants.AUTHENTICATE_SERVICE_0007, Constants.AUTHENTICATE_SERVICE_0007_DESCRIPTION, errorContext);
                    throw new CustomException(errorMessage);
                }
            }
        /*} catch (Exception e){
            logger.error("Failed to get login info: " + e.getMessage());
        }*/
        errorContext = new ErrorContext(null, null, "Exception occurs.");
        ErrorMessage errorMessage = new ErrorMessage(Constants.AUTHENTICATE_SERVICE_0005, Constants.AUTHENTICATE_SERVICE_0005_DESCRIPTION, errorContext);
        throw new CustomException(errorMessage);
        //return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObj.toString()).build();
    }
}
