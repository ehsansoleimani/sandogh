package com.sandogh.sandogh.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @Length(min = 2, message = "username lenght should not be less than 2 chars")
    @NotNull
    private String username;
    @Length(min = 6, message = "password lenght should not be less than 6 chars")
    @NotNull
    private String password;
    @Length(min = 8, message = "phonenumber lenght should not be less than 8 chars")
    @NotNull
    private String phoneNumber;
    @Email(message = "email is invalid")
    @NotNull
    private String email;
}
