package com.dsi.authentication.dao;

import com.dsi.authentication.model.UserSession;

/**
 * Created by sabbir on 6/15/16.
 */
public interface UserSessionDao {

    boolean saveUserSession(UserSession userSession);

    boolean updateUserSession(UserSession userSession);

    boolean deleteUserSession(UserSession userSession);

    UserSession getUserSessionByUserIdAndAccessToken(String userID, String accessToken);
}
