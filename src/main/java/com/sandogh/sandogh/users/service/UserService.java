package com.sandogh.sandogh.users.service;


import com.sandogh.sandogh.base.utils.PasswordEncryptionUtils;
import com.sandogh.sandogh.users.dao.UserDAO;
import com.sandogh.sandogh.users.entity.User;
import com.sandogh.sandogh.users.exceptions.UsernameAlreadyTakeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * @author Ehsan Soleimani (esoleimani@voipfuture.com)
 **/
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDAO userDAO;

    public List<User> getAllUser(){
        return (List<User>) userDAO.findAll();
    }

    public void getDeleteUser(long id){
        this.userDAO.deleteById(id);
    };
    public void saveUser(User user){
        this.userDAO.save(user);
    }

    public User getUserById(long id){
        Optional<User> optional=userDAO.findById(id);
        User user=null;
        if(optional.isPresent()){
            user=optional.get();
        }else
        {
            throw new RuntimeException("user not found for id ::" + id);
        }
        return user;
    }

    private final Object CREATE_USER_LOCK = new Object();

    //public User createNewUser(UserDTO user);
    public User createNewUser(String username, String password, String email, String phoneNumber) throws UsernameAlreadyTakeException {
        String hashedPassword = PasswordEncryptionUtils.getHashedPasswordAndSaltCombination(password);
        User user = new User(username, hashedPassword, phoneNumber, email);
        return userDAO.save(user);
    }

    public boolean userByEmailExists(String email) {
        return userDAO.existsByEmail(email);
    }
}
