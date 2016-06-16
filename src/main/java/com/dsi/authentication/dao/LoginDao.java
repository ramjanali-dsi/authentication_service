package com.dsi.authentication.dao;

import com.dsi.authentication.model.Login;

/**
 * Created by sabbir on 6/16/16.
 */
public interface LoginDao {

    void updateLoginInfo(Login login);

    Login getLoginInfo(String userID, String email);

    Login getLoginInfoByResetToken(String resetToken);
}
