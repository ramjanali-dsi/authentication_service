package com.dsi.authentication.util;

import scala.util.parsing.combinator.testing.Str;

/**
 * Created by sabbir on 6/15/16.
 */
public class Constants {

    public static final String AUTHORIZATION = "authorization";
    public static final String TENANT_ID = "tenant_id";

    public static final String SYSTEM = "system";
    public static final String SYSTEM_HEADER_ID = "0759a760-b9df-4c8a-8a64-fa31f256d13b";

    static final String SYSTEM_ID = "425744ba-6c10-47c0-91cf-5a4c05265b56";

    static final Long NOTIFICATION_EMAIL_TYPE_ID = 1L;
    public static final Long RESET_PASS_TEMPLATE_ID = 8L;
    public static final Long RESET_PASS_CHANGE_TEMPLATE_ID = 9L;
    public static final Long PASS_CHANGE_TEMPLATE_ID = 10L;

    public static final int TIME_INTERVAL = 30000 * 60;

    public static final String SALT = "87c63aae-917c-42ce-b4c7-8a4847db4133";
    public static final String SECRET_KEY = "MTUxMWFlMjctNWZjNS00YmVlLWJlZTMtYmRkNTY2ZWQyY2E3NDg2YTNlNDktNTA0MS00NWRjLTg3NDktNGU5NGJhY2IwN2M3";

    public static final String MESSAGE = "message";

    // Error Code
    public static final String AUTHENTICATE_SERVICE_0001 = "authenticate_service_0001";
    public static final String AUTHENTICATE_SERVICE_0001_DESCRIPTION = "Not defined.";

    public static final String AUTHENTICATE_SERVICE_0002 = "authenticate_service_0002";
    public static final String AUTHENTICATE_SERVICE_0002_DESCRIPTION = "Create failed.";

    public static final String AUTHENTICATE_SERVICE_0003 = "authenticate_service_0003";
    public static final String AUTHENTICATE_SERVICE_0003_DESCRIPTION = "Update failed.";

    public static final String AUTHENTICATE_SERVICE_0004 = "authenticate_service_0004";
    public static final String AUTHENTICATE_SERVICE_0004_DESCRIPTION = "Delete failed.";

    public static final String AUTHENTICATE_SERVICE_0005 = "authenticate_service_0005";
    public static final String AUTHENTICATE_SERVICE_0005_DESCRIPTION = "Not found.";

    public static final String AUTHENTICATE_SERVICE_0006 = "authenticate_service_0006";
    public static final String AUTHENTICATE_SERVICE_0006_DESCRIPTION = "Instance initialization failed.";

    public static final String AUTHENTICATE_SERVICE_0007 = "authenticate_service_0007";
    public static final String AUTHENTICATE_SERVICE_0007_DESCRIPTION = "Token expired.";

    static final String AUTHENTICATE_SERVICE_0008 = "authenticate_service_0008";
    static final String AUTHENTICATE_SERVICE_0008_DESCRIPTION = "Params are missing.";

    public static final String AUTHENTICATE_SERVICE_0009 = "authenticate_service_0009";
    public static final String AUTHENTICATE_SERVICE_0009_DESCRIPTION = "JSON read/write failed.";

    public static final String AUTHENTICATE_SERVICE_0010 = "authenticate_service_0010";
    public static final String AUTHENTICATE_SERVICE_0010_DESCRIPTION = "Not match.";

    public static final String AUTHENTICATE_SERVICE_0011 = "authenticate_service_0011";
    public static final String AUTHENTICATE_SERVICE_0011_DESCRIPTION = "Password hash failed.";

    public static final String AUTHENTICATE_SERVICE_0012 = "authenticate_service_0012";
    public static final String AUTHENTICATE_SERVICE_0012_DESCRIPTION = "Error from another api call.";
}
