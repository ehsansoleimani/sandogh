package com.sandogh.sandogh.role.dao;

import com.sandogh.sandogh.role.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleDao extends CrudRepository<Role,Long> {

    public Role deleteByUser(long id);
}
