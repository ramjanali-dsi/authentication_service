package com.dsi.authentication.service.impl;

import com.dsi.authentication.dao.DBLoginHandlerDao;
import com.dsi.authentication.dao.impl.DBLoginHandlerDaoImpl;
import com.dsi.authentication.model.Login;
import com.dsi.authentication.service.LoginHandler;

/**
 * Created by sabbir on 6/15/16.
 */
public class DBLoginHandlerImpl implements LoginHandler {

    private static final DBLoginHandlerDao dao = new DBLoginHandlerDaoImpl();

    @Override
    public Login validateUser(String username, String password) {
        return dao.getLoginInfo(username, password);
    }
}
