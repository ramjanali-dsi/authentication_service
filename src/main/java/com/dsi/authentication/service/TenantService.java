package com.dsi.authentication.service;

import com.dsi.authentication.model.Tenant;

/**
 * Created by sabbir on 6/15/16.
 */
public interface TenantService {

    void saveTenant(Tenant tenant);

    void updateTenant(Tenant tenant);

    Tenant getTenantByID(String tenantID);
}
