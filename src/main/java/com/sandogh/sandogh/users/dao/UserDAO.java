package com.sandogh.sandogh.users.dao;

import com.sandogh.sandogh.users.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @author Ehsan Soleimani (esoleimani@voipfuture.com)
 **/
@Repository
public interface UserDAO extends CrudRepository<User, Long> {

    boolean existsByEmail(String email);

    public User findByEmail(String email);

    boolean existsById(long id);

    boolean existsByToken(String token);

    @Query("update User u set u.active=true where u.token=:token ")
    @Modifying
    @Transactional
    public void activeUserByToken(@Param("token") String token);


}
