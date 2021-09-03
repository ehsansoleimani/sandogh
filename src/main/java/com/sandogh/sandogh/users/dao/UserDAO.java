package com.sandogh.sandogh.users.dao;

import com.sandogh.sandogh.users.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ehsan Soleimani (esoleimani@voipfuture.com)
 **/
@Repository
public interface UserDAO extends CrudRepository<User, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    public User findByEmail(String email);
}
