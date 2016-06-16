package com.dsi.authentication.service;

import com.dsi.authentication.model.Login;

/**
 * Created by sabbir on 6/16/16.
 */
public interface LoginService {

    void updateLoginInfo(Login login);

    Login getLoginInfo(String userID, String email);

    Login getLoginInfoByResetToken(String resetToken);
}
