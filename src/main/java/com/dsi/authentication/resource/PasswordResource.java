package com.dsi.authentication.resource;

import com.dsi.authentication.exception.CustomException;
import com.dsi.authentication.exception.ErrorContext;
import com.dsi.authentication.exception.ErrorMessage;
import com.dsi.authentication.model.Login;
import com.dsi.authentication.service.LoginService;
import com.dsi.authentication.service.TokenService;
import com.dsi.authentication.service.impl.EmailProvider;
import com.dsi.authentication.service.impl.LoginServiceImpl;
import com.dsi.authentication.service.impl.TokenServiceImpl;
import com.dsi.authentication.util.Constants;
import com.dsi.authentication.util.PasswordHash;
import com.dsi.authentication.util.Utility;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sabbir on 6/16/16.
 */

@Path("/v1/password")
@Api(value = "/Authentication", description = "Operations about Authentication")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.WILDCARD})
public class PasswordResource {

    private static final Logger logger = Logger.getLogger(PasswordResource.class);

    private static final TokenService tokenService = new TokenServiceImpl();
    private static final LoginService loginService = new LoginServiceImpl();

    @Context
    HttpServletRequest request;

    @POST
    @Path("/reset")
    @ApiOperation(value = "Reset Password Request", notes = "Reset Password Request", position = 5)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reset password request success"),
            @ApiResponse(code = 500, message = "Reset password request failed, unauthorized.")
    })
    public Response resetPasswordRequest(String requestBody) throws CustomException {
        JSONObject responseObj = new JSONObject();
        JSONObject requestObj;

        try {
            logger.info("Request Body: " + requestBody);

            requestObj = new JSONObject(requestBody);
            String username = Utility.validation(requestObj, "username");
            String resetUrl = Utility.validation(requestObj, "reset_url");

            Login login = loginService.getLoginInfo(null, username);

            String token = Utility.generateRandomString();

            login.setResetPasswordToken(token);
            login.setResetTokenExpireTime(DateUtils.addHours(Utility.today(), 1));
            login.setModifiedDate(Utility.today());

            loginService.updateLoginInfo(login);
            logger.info("Update login info successfully.");

            EmailProvider.constructResetPasswordRequestToken(login.getEmail(), resetUrl + token);
            logger.info("An email sent to the user for reset password.");

            responseObj.put(Constants.MESSAGE, "Reset password request success");
            return Response.ok().entity(responseObj.toString()).build();

        } catch (JSONException je){
            ErrorContext errorContext = new ErrorContext(null, null, je.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.AUTHENTICATE_SERVICE_0009,
                    Constants.AUTHENTICATE_SERVICE_0009_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @POST
    @Path("/reset/{reset_token}")
    @ApiOperation(value = "Change Password From Reset Request", notes = "Change Password From Reset Request", position = 6)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Change password from reset request success"),
            @ApiResponse(code = 500, message = "change password from reset request failed, unauthorized.")
    })
    public Response changePasswordFromResetRequest(@PathParam("reset_token") String resetToken,
                                                   String requestBody) throws CustomException {
        JSONObject responseObj = new JSONObject();
        JSONObject requestObj;
        ErrorContext errorContext;
        ErrorMessage errorMessage;

        try {
            logger.info("Request Body: " + requestBody);

            requestObj = new JSONObject(requestBody);
            String newPassword = Utility.validation(requestObj, "new_password");
            String confirmPassword = Utility.validation(requestObj, "confirm_password");

            Login login = loginService.getLoginInfoByResetToken(resetToken);
            if (newPassword.equals(confirmPassword)) {
                String hashPassword = PasswordHash.hash(newPassword, login.getSalt());

                login.setResetPasswordToken(null);
                login.setResetTokenExpireTime(null);
                login.setPassword(hashPassword);
                login.setModifiedDate(Utility.today());

                loginService.updateLoginInfo(login);
                logger.info("Update login info successfully.");

                logger.info("Password reset successfully.");
                responseObj.put(Constants.MESSAGE, "Password reset success");
                return Response.ok().entity(responseObj.toString()).build();

            } else {
                errorContext = new ErrorContext(null, "Password", "Password not match.");
                errorMessage = new ErrorMessage(Constants.AUTHENTICATE_SERVICE_0010,
                        Constants.AUTHENTICATE_SERVICE_0010_DESCRIPTION, errorContext);
                throw new CustomException(errorMessage);
            }
        } catch (JSONException je){
            errorContext = new ErrorContext(null, null, je.getMessage());
            errorMessage = new ErrorMessage(Constants.AUTHENTICATE_SERVICE_0009,
                    Constants.AUTHENTICATE_SERVICE_0009_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @POST
    @Path("/change")
    @ApiOperation(value = "Change Password", notes = "Change Password", position = 7)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Change password success"),
            @ApiResponse(code = 500, message = "change password failed, unauthorized.")
    })
    public Response changePassword(String requestBody) throws CustomException {
        String accessToken = request.getAttribute("access_token") != null ?
                request.getAttribute("access_token").toString() : null;

        JSONObject responseObj = new JSONObject();
        JSONObject requestObj;
        ErrorContext errorContext;
        ErrorMessage errorMessage;

        try {
            logger.info("Request Body: " + requestBody);

            requestObj = new JSONObject(requestBody);
            String oldPassword = Utility.validation(requestObj, "old_password");
            String newPassword = Utility.validation(requestObj, "new_password");
            String confirmPassword = Utility.validation(requestObj, "confirm_password");

            Claims parseToken = tokenService.parseToken(accessToken);

            Login login = loginService.getLoginInfo(parseToken.getId(), null);
            String hashPassword = PasswordHash.hash(oldPassword, login.getSalt());

            if (newPassword.equals(confirmPassword) &&
                    hashPassword.equals(login.getPassword())) {

                String newHashPassword = PasswordHash.hash(newPassword, login.getSalt());
                login.setPassword(newHashPassword);
                login.setModifiedDate(Utility.today());
                loginService.updateLoginInfo(login);
                logger.info("Update login info successfully.");

                responseObj.put(Constants.MESSAGE, "Password change success");
                return Response.ok().entity(responseObj.toString()).build();

            } else {
                errorContext = new ErrorContext(null, "Password", "Password not match.");
                errorMessage = new ErrorMessage(Constants.AUTHENTICATE_SERVICE_0010,
                        Constants.AUTHENTICATE_SERVICE_0010_DESCRIPTION, errorContext);
                throw new CustomException(errorMessage);
            }
        } catch (JSONException je){
            errorContext = new ErrorContext(null, null, je.getMessage());
            errorMessage = new ErrorMessage(Constants.AUTHENTICATE_SERVICE_0009,
                    Constants.AUTHENTICATE_SERVICE_0009_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }
}
