package com.dsi.authentication.service.impl;

import com.dsi.authentication.dao.UserSessionDao;
import com.dsi.authentication.dao.impl.UserSessionDaoImpl;
import com.dsi.authentication.model.UserSession;
import com.dsi.authentication.service.UserSessionService;

/**
 * Created by sabbir on 6/15/16.
 */
public class UserSessionServiceImpl implements UserSessionService {

    private static final UserSessionDao dao = new UserSessionDaoImpl();

    @Override
    public void saveUserSession(UserSession userSession){
        dao.saveUserSession(userSession);
    }

    @Override
    public void updateUserSession(UserSession userSession){
        dao.updateUserSession(userSession);
    }

    @Override
    public void deleteUserSession(UserSession userSession){
        dao.deleteUserSession(userSession);
    }

    @Override
    public UserSession getUserSessionByUserIdAndAccessToken(String userID, String accessToken){
        return dao.getUserSessionByUserIdAndAccessToken(userID, accessToken);
    }
}
