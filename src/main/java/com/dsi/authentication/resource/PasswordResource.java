package com.dsi.authentication.resource;

import com.dsi.authentication.model.Login;
import com.dsi.authentication.service.LoginService;
import com.dsi.authentication.service.UserSessionService;
import com.dsi.authentication.service.impl.EmailProvider;
import com.dsi.authentication.service.impl.LoginServiceImpl;
import com.dsi.authentication.service.impl.TokenServiceImpl;
import com.dsi.authentication.service.impl.UserSessionServiceImpl;
import com.dsi.authentication.util.Constants;
import com.dsi.authentication.util.PasswordSaltUtil;
import com.dsi.authentication.util.Utils;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
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

    private static final TokenServiceImpl tokenService = new TokenServiceImpl();
    private static final LoginService loginService = new LoginServiceImpl();
    private static final UserSessionService userSessionService = new UserSessionServiceImpl();

    @POST
    @Path("/reset")
    @ApiOperation(value = "Reset Password Request", notes = "Reset Password Request", position = 5)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reset password request success"),
            @ApiResponse(code = 500, message = "Reset password request failed, unauthorized.")
    })
    public Response resetPasswordRequest(String requestBody){
        JSONObject responseObj = new JSONObject();
        JSONObject requestObj;
        try{
            logger.info("Request Body: " + requestBody);

            requestObj = new JSONObject(requestBody);
            String username = Utils.validation(requestObj, "username");
            String resetUrl = Utils.validation(requestObj, "reset_url");

            if(!Utils.isNullOrEmpty(username) && !Utils.isNullOrEmpty(resetUrl)){
                Login login = loginService.getLoginInfo(null, username);
                if(login != null){
                    String token = Utils.generateRandomString();

                    login.setResetPasswordToken(token);
                    login.setResetTokenExpireTime(DateUtils.addHours(Utils.today(), 1));
                    login.setModifiedDate(Utils.today());

                    loginService.updateLoginInfo(login);
                    logger.info("Update login info successfully.");

                    EmailProvider.constructResetPasswordRequestToken(login.getEmail(), resetUrl + token);
                    logger.info("An email sent to the user for reset password.");

                    responseObj.put("message", "Reset password request success");
                    return Response.ok().entity(responseObj.toString()).build();
                }
            }
        } catch (Exception e){
            logger.error("Failed to reset password request: " + e.getMessage());
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObj.toString()).build();
    }

    @POST
    @Path("/change/{reset_token}")
    @ApiOperation(value = "Change Password From Reset Request", notes = "Change Password From Reset Request", position = 6)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Change password from reset request success"),
            @ApiResponse(code = 500, message = "change password from reset request failed, unauthorized.")
    })
    public Response changePasswordFromResetRequest(@PathParam("reset_token") String resetToken, String requestBody){
        JSONObject responseObj = new JSONObject();
        JSONObject requestObj;
        try{
            logger.info("Request Body: " + requestBody);

            requestObj = new JSONObject(requestBody);
            String newPassword = Utils.validation(requestObj, "new_password");
            String confirmPassword = Utils.validation(requestObj, "confirm_password");

            if(!Utils.isNullOrEmpty(newPassword) && !Utils.isNullOrEmpty(confirmPassword)
                    && !Utils.isNullOrEmpty(resetToken)){

                Login login = loginService.getLoginInfoByResetToken(resetToken);
                if(login != null && newPassword.equals(confirmPassword)){
                    String hashPassword = PasswordSaltUtil.hash(newPassword, login.getSalt());

                    login.setResetPasswordToken(null);
                    login.setResetTokenExpireTime(null);
                    login.setPassword(hashPassword);
                    login.setModifiedDate(Utils.today());

                    loginService.updateLoginInfo(login);
                    logger.info("Update login info successfully.");

                    logger.info("Password reset successfully.");
                    responseObj.put("message", "Password reset success");
                    return Response.ok().entity(responseObj.toString()).build();
                }
            }
        } catch (Exception e){
            logger.error("Failed to change password from reset request: " + e.getMessage());
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObj.toString()).build();
    }
}
