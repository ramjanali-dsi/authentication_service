package com.dsi.authentication.util;

/**
 * Created by sabbir on 6/15/16.
 */
public class Constants {

    public static final String AUTHORIZATION = "authorization";

    public static final int TIME_INTERVAL = 10000 * 60;

    public static final String SECRET_KEY = "MTUxMWFlMjctNWZjNS00YmVlLWJlZTMtYmRkNTY2ZWQyY2E3NDg2YTNlNDktNTA0MS00NWRjLTg3NDktNGU5NGJhY2IwN2M3";

    public static final String MESSAGE = "message";

    // Error Code
    public static final String ID = "id";
    public static final String ENTITY = "entity";

    public static final String AUTHENTICATE_SERVICE_0001 = "authenticate_service_0001";
    public static final String AUTHENTICATE_SERVICE_0001_DESCRIPTION = "Login failed, username & password doesn't match.";

    public static final String AUTHENTICATE_SERVICE_0002 = "authenticate_service_0002";
    public static final String AUTHENTICATE_SERVICE_0002_DESCRIPTION = "Auth handler class name not found.";

    public static final String AUTHENTICATE_SERVICE_0003 = "authenticate_service_0003";
    public static final String AUTHENTICATE_SERVICE_0003_DESCRIPTION = "Tenant not found.";

    public static final String AUTHENTICATE_SERVICE_0004 = "authenticate_service_0004";
    public static final String AUTHENTICATE_SERVICE_0004_DESCRIPTION = "Params are not found.";

    public static final String AUTHENTICATE_SERVICE_0005 = "authenticate_service_0005";
    public static final String AUTHENTICATE_SERVICE_0005_DESCRIPTION = "Internal server error.";

    public static final String AUTHENTICATE_SERVICE_0006 = "authenticate_service_0006";
    public static final String AUTHENTICATE_SERVICE_0006_DESCRIPTION = "User session not found.";

    public static final String AUTHENTICATE_SERVICE_0007 = "authenticate_service_0007";
    public static final String AUTHENTICATE_SERVICE_0007_DESCRIPTION = "Token expired.";

    public static final String AUTHENTICATE_SERVICE_0008 = "authenticate_service_0008";
    public static final String AUTHENTICATE_SERVICE_0008_DESCRIPTION = "Header not found.";

}
