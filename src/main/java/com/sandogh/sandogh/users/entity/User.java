package com.sandogh.sandogh.users.entity;

import com.sandogh.sandogh.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author Ehsan Soleimani (esoleimani@voipfuture.com)
 **/
@Entity
@Table(name = "table_user" )
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(name = "user_name",unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "email" , unique = true)
    private String email;
}
