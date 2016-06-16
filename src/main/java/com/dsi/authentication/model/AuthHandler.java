package com.dsi.authentication.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sabbir on 6/9/16.
 */

@Entity
@Table(name = "ref_auth_handler")
@Data
public class AuthHandler {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "auth_handler_id", length = 40)
    private String authHandlerId;

    @Column(length = 50)
    private String name;

    @Column(name = "type_impl", nullable = false, length = 100)
    private String typeImpl;

    private int version;
}
