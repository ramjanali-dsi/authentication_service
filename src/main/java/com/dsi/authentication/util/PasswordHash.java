package com.dsi.authentication.util;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

/**
 * Created by sabbir on 6/15/16.
 */
public class PasswordHash {

    private static final int iterations = 1000;
    private static final int desiredKeyLen = 64 * 8;

    public static String hash(String password, String salts) throws Exception {
        if (Utility.isNullOrEmpty(password))
            throw new IllegalArgumentException("Empty passwords are not supported.");

        byte[] salt = salts.getBytes();

        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = f.generateSecret(new PBEKeySpec(
                password.toCharArray(), salt, iterations, desiredKeyLen)
        );

        byte[] hash = key.getEncoded();
        return iterations + ":" + toHex(hash);
    }

    private static String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }
}
