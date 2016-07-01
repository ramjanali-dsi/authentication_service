package com.dsi.authentication.util;

import com.dsi.authentication.filter.AccessTokenFilter;
import com.dsi.authentication.filter.ResponseCORSFilter;
import com.dsi.authentication.resource.LoginResource;
import com.dsi.checkauthorization.filter.CheckAuthorizationFilter;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by sabbir on 6/16/16.
 */
public class AuthenticateService extends ResourceConfig {

    public AuthenticateService(){
        packages(LoginResource.class.getPackage().getName());
        register(ResponseCORSFilter.class);
        //register(AccessTokenFilter.class);

        register(CheckAuthorizationFilter.class);

        //SessionUtil.getSession();
    }
}
