package com.sandogh.sandogh.users.service;

import com.sandogh.sandogh.base.utils.PasswordEncryptionUtils;
import com.sandogh.sandogh.users.dao.UserDAO;
import com.sandogh.sandogh.users.entity.User;
import com.sandogh.sandogh.users.exceptions.UsernameAlreadyTakeException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

/**
 * @author Ehsan Soleimani (esoleimani@voipfuture.com)
 **/
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDAO userDAO;

    private final Object CREATE_USER_LOCK = new Object();

    public User createNewUser(String username, String password, String email, String phoneNumber) throws UsernameAlreadyTakeException {
        Validate.notNull(username);
        Validate.notBlank(username);
        Validate.notNull(password);
        Validate.notBlank(password);
        //TODO: validate username
        //TODO: validate password
        //TODO: validate email
        //TODO: validate phone number
        synchronized (CREATE_USER_LOCK) {
            if (userDAO.existsByUsername(username)) {
                throw new UsernameAlreadyTakeException(username);
            }
            String hashedPassword = PasswordEncryptionUtils.getHashedPasswordAndSaltCombination(password);
            User user = new User(username, hashedPassword, phoneNumber, email);
            return userDAO.save(user);
        }
    }

}
