package com.dsi.authentication.service;

import com.dsi.authentication.exception.CustomException;
import com.dsi.authentication.model.UserSession;

/**
 * Created by sabbir on 6/15/16.
 */
public interface UserSessionService {

    void saveUserSession(UserSession userSession) throws CustomException;

    void updateUserSession(UserSession userSession) throws CustomException;

    void deleteUserSession(UserSession userSession) throws CustomException;

    UserSession getUserSessionByUserIdAndAccessToken(String userID, String accessToken) throws CustomException;
}
