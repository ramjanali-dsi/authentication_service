package com.dsi.authentication.dao;

import com.dsi.authentication.model.UserSession;

/**
 * Created by sabbir on 6/15/16.
 */
public interface UserSessionDao {

    void saveUserSession(UserSession userSession);

    void updateUserSession(UserSession userSession);

    void deleteUserSession(UserSession userSession);

    UserSession getUserSessionByUserIdAndAccessToken(String userID, String accessToken);
}
