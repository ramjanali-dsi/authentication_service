package com.dsi.authentication.resource;

import com.dsi.authentication.model.UserSession;
import com.dsi.authentication.service.LoginFactory;
import com.dsi.authentication.service.TenantService;
import com.dsi.authentication.service.UserSessionService;
import com.dsi.authentication.service.impl.LoginFactoryImpl;
import com.dsi.authentication.service.impl.TenantServiceImpl;
import com.dsi.authentication.service.impl.TokenServiceImpl;
import com.dsi.authentication.service.impl.UserSessionServiceImpl;
import com.dsi.authentication.util.Constants;
import com.dsi.authentication.util.Utils;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import io.jsonwebtoken.Claims;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sabbir on 6/16/16.
 */

@Path("/v1/access_token")
@Api(value = "/Authentication", description = "Operations about Authentication")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.WILDCARD})
public class TokenResource {

    private static final Logger logger = Logger.getLogger(TokenResource.class);

    private static final UserSessionService userSessionService = new UserSessionServiceImpl();
    private static final TokenServiceImpl tokenService = new TokenServiceImpl();

    @Context
    HttpServletRequest request;

    @GET
    @ApiOperation(value = "Reset Access Token", notes = "Reset Access Token", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reset token success"),
            @ApiResponse(code = 500, message = "Reset token failed, unauthorized.")
    })
    public Response resetAccessToken(){
        String accessToken = request.getAttribute("access_token") != null ?
                request.getAttribute("access_token").toString() : null;

        JSONObject responseObj = new JSONObject();
        JSONObject parseObj;
        try{
            if (accessToken != null) {

                Claims parseToken = tokenService.parseToken(accessToken);
                if (parseToken != null) {

                    String subject = parseToken.getSubject();
                    logger.info("Parse object: " + subject);

                    parseObj = new JSONObject(subject);
                    String userID = Utils.validation(parseObj, "user_id");

                    UserSession userSession = userSessionService.getUserSessionByUserIdAndAccessToken(userID, accessToken);
                    if(userSession != null){
                        String newAccessToken = tokenService.createToken(parseToken.getId(), parseToken.getIssuer(),
                                parseToken.getSubject(), Constants.TIME_INTERVAL);

                        logger.info("Generate Access Token: " + newAccessToken);
                        responseObj.put("access_token", newAccessToken);

                        userSession.setAccessToken(newAccessToken);
                        userSession.setModifiedDate(Utils.today());
                        userSession.setModifiedBy(userID);
                        userSessionService.updateUserSession(userSession);
                        logger.info("User session updated successfully.");

                        return Response.ok().entity(responseObj.toString()).build();
                    }
                }
            }
        } catch (Exception e){
            logger.error("Failed to reset access token: " + e.getMessage());
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObj.toString()).build();
    }
}
