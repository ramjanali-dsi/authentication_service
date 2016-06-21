package com.dsi.authentication.service;

import com.dsi.authentication.exception.CustomException;
import com.dsi.authentication.model.Login;

/**
 * Created by sabbir on 6/16/16.
 */
public interface LoginService {

    void updateLoginInfo(Login login) throws CustomException;

    Login getLoginInfo(String userID, String email) throws CustomException;

    Login getLoginInfoByResetToken(String resetToken) throws CustomException;
}
