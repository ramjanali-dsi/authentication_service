package com.dsi.authentication.service.impl;

import com.dsi.authentication.dao.TenantDao;
import com.dsi.authentication.dao.impl.TenantDaoImpl;
import com.dsi.authentication.model.Tenant;
import com.dsi.authentication.service.TenantService;

/**
 * Created by sabbir on 6/15/16.
 */
public class TenantServiceImpl implements TenantService {

    private static final TenantDao tenantDao = new TenantDaoImpl();

    @Override
    public void saveTenant(Tenant tenant) {
        tenantDao.saveTenant(tenant);
    }

    @Override
    public void updateTenant(Tenant tenant) {
        tenantDao.updateTenant(tenant);
    }

    @Override
    public Tenant getTenantByID(String tenantID) {
        return tenantDao.getTenantByID(tenantID);
    }
}
