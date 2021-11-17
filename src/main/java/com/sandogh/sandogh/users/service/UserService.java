package com.sandogh.sandogh.users.service;

import com.sandogh.sandogh.base.exceptions.ServiceException;
import com.sandogh.sandogh.base.utils.PasswordEncryptionUtils;
import com.sandogh.sandogh.users.UserDTO;
import com.sandogh.sandogh.users.dao.UserDAO;
import com.sandogh.sandogh.users.entity.User;
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

    public User createNewUser(UserDTO user) throws ServiceException {
        return null;// TODO: 18.11.21 to be implemented
    }

    public User updateUser(UserDTO user) throws ServiceException {
        return null; // TODO: 23.11.21
    }

    public User createNewUser(String username, String password, String email, String phoneNumber) throws ServiceException {
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
                throw new ServiceException(UserServiceErrorCodes.USER_ALREADY_EXISTS, username);
            }
            String hashedPassword = PasswordEncryptionUtils.getHashedPasswordAndSaltCombination(password);
            User user = new User(username, hashedPassword, phoneNumber, email);
            return userDAO.save(user);
        }
    }

    public interface UserServiceErrorCodes {
        static String getErrorCode(String code) {
            return USER_SERVICE_ERROR_PREFIX + code;
        }
        String USER_SERVICE_ERROR_PREFIX = "user.";
        String USER_ALREADY_EXISTS = USER_SERVICE_ERROR_PREFIX + "alreadyExists";
        String USER_NOT_EXISTS = "notExists";
    }
}
