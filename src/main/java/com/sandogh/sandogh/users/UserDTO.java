package com.sandogh.sandogh.users;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {
    private Long id;
    private String email;
    private transient String plainTextPassword; //duo to security reasons plain text password won't be serialized
    private String phoneNumber;
}
