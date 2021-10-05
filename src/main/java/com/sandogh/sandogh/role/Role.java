package com.sandogh.sandogh.role;

import com.sandogh.sandogh.base.entity.BaseEntity;
import com.sandogh.sandogh.users.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity {

    private String role;

    @ManyToOne
    private User user;
}
