package com.dsi.authentication.service;

import com.dsi.authentication.model.UserSession;

import java.util.List;

/**
 * Created by sabbir on 6/15/16.
 */
public interface UserSessionService {

    void saveUserSession(UserSession userSession);

    void updateUserSession(UserSession userSession);

    void deleteUserSession(UserSession userSession);

    UserSession getUserSessionByUserIdAndAccessToken(String userID, String accessToken);
}
