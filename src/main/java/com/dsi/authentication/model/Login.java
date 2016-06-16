package com.dsi.authentication.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sabbir on 6/10/16.
 */

@Entity
@Table(name = "dsi_login")
@Data
public class Login {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "login_id", length = 40)
    private String loginId;

    @Column(unique = true, nullable = false, length = 40)
    private String email;

    @Column(length = 50, nullable = false)
    private String password;

    @Column(length = 50)
    private String salt;

    @Column(name = "first_name", length = 40)
    private String firstName;

    @Column(name = "last_name", length = 40)
    private String lastName;

    @Column(name = "user_id", nullable = false, length = 40)
    private String userId;

    @Column(name = "reset_password_token", length = 200)
    private String resetPasswordToken;

    @Column(name = "reset_token_expire_time")
    private Date resetTokenExpireTime;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "created_by", nullable = false, length = 40)
    private String createBy;

    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "modified_by", nullable = false, length = 40)
    private String modifiedBy;

    private int version;

}
