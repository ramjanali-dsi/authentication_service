package com.dsi.authentication.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sabbir on 6/9/16.
 */

@Entity
@Table(name = "dsi_tenant")
@Data
public class Tenant {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "tenant_id", length = 40)
    private String tenantId;

    @Column(length = 50)
    private String name;

    @Column(name = "secret_key", nullable = false, length = 50)
    private String secretKey;

    @Column(name = "short_name", length = 40)
    private String shortName;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToOne
    @JoinColumn(name = "auth_handler_id", nullable = false)
    private AuthHandler authHandler;

    private int version;
}
