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
@Table(name = "table_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(name = "user_name", unique = true)
    @Length(min = 2, message = "username lenght should not be less than 2 chars")
    private String username;

    @Column(name = "password")
    @Length(min = 6, message = "password lenght should not be less than 6 chars")
    private String password;

    @Column(name = "phonenumber")
    @Length(min = 8, message = "phonenumber lenght should not be less than 8 chars")
    private String phoneNumber;

    @Column(name = "email", unique = true)
    @Email(message = "email is invalid")
    @NotNull
    private String email;
}
