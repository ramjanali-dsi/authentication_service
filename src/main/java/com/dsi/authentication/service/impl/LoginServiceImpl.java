package com.dsi.authentication.service.impl;

import com.dsi.authentication.dao.LoginDao;
import com.dsi.authentication.dao.impl.LoginDaoImpl;
import com.dsi.authentication.model.Login;
import com.dsi.authentication.service.LoginService;

/**
 * Created by sabbir on 6/16/16.
 */
public class LoginServiceImpl implements LoginService {

    private static final LoginDao dao = new LoginDaoImpl();

    @Override
    public void updateLoginInfo(Login login) {
        dao.updateLoginInfo(login);
    }

    @Override
    public Login getLoginInfo(String userID, String email) {
        return dao.getLoginInfo(userID, email);
    }

    @Override
    public Login getLoginInfoByResetToken(String resetToken) {
        return dao.getLoginInfoByResetToken(resetToken);
    }
}
