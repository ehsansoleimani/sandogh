package com.sandogh.sandogh.users.service;

import com.sandogh.sandogh.base.exceptions.ServiceException;
import com.sandogh.sandogh.role.dao.RoleDao;
import com.sandogh.sandogh.users.dao.UserDAO;
import com.sandogh.sandogh.users.entity.User;
import com.sandogh.sandogh.utils.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Ehsan Soleimani (esoleimani@voipfuture.com)
 **/
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDAO userDAO;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    RoleDao roleDao;

    public List<User> getAllUser() {
        return (List<User>) userDAO.findAll();
    }

    public void getDeleteUser(long id) {

        this.userDAO.deleteById(id);
    }

    public User getEmail(String email) {
        return this.userDAO.findByEmail(email);
    }

    public void saveUser(User user) {
        this.userDAO.save(user);
    }

    public User getUserById(long id) throws ServiceException {
        Optional<User> optional = userDAO.findById(id);

        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new ServiceException(UserServiceErrorMessages.USER_NOT_FOUND, id);
        }

    }

    private final Object CREATE_USER_LOCK = new Object();

    public User createNewUser(User user) throws ServiceException {
        ;
        if (userDAO.existsByEmail(user.getEmail())) {
            throw new ServiceException(UserServiceErrorMessages.USER_ALREADY_EXISTS, user.getEmail());
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setCreationDate(LocalDateTime.now());
        user.setActive(false);
        String token = String.format("%s%s", bCryptPasswordEncoder.encode(user.getEmail()),
                TokenUtil.generateRandomToken());
        token = token.replace("/", "");
        user.setToken(token);
        String activationLink = String.format(
                "<a href='http://127.0.0.1:8080/activation/%s'>active your account </a>", user.getToken());
        send(user.getEmail(), "activation link", activationLink);
        return userDAO.save(user);
    }

    public boolean userByEmailExists(String email) {
        return userDAO.existsByEmail(email);
    }

    public boolean userByIdExists(long id) {
        return userDAO.existsById(id);
    }

    public void send(String to, String subject, String body) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper
                = new MimeMessageHelper(mimeMessage);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    mimeMessageHelper.addTo(to);
                    mimeMessageHelper.setSubject(subject);
                    mimeMessageHelper.setText(body);
                } catch (MessagingException e) {
                    e.printStackTrace();
                } finally {
                    javaMailSender.send(mimeMessage);
                }

            }
        });
    }

    public boolean userByTokenExists(String token) {
        return userDAO.existsByToken(token);
    }

    public void activeUser(String token) {
        userDAO.activeUserByToken(token);
    }


    public interface UserServiceErrorMessages {
        String USER_SERVICE_ERROR_PREFIX = "user.";
        String USER_ALREADY_EXISTS = USER_SERVICE_ERROR_PREFIX + "alreadyExists";
        String USER_NOT_FOUND = USER_SERVICE_ERROR_PREFIX + "notFound";
    }
}
