package com.sandogh.sandogh.users;

import com.sandogh.sandogh.base.exceptions.ServiceException;
import com.sandogh.sandogh.users.dao.UserDAO;
import com.sandogh.sandogh.users.entity.User;
import com.sandogh.sandogh.users.service.UserService;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class UserServiceTest {
    private static final String ACCEPTABLE_EMAIL = "foo@bar.foobar";
    private static final String ACCEPTABLE_PASSWORD = "1234";
    private static final String ACCEPTABLE_NUMBER= "+123456890";

    private UserService userService;
    private UserDAO dao;

    @Before
    public void init() {
        dao= createMock(UserDAO.class);
        userService = new UserService(dao);
    }

    @Test
    public void testCreateUserEmailExists() {
        String email = ACCEPTABLE_EMAIL;
        UserDTO userDTO = createNewUserDTO(email, ACCEPTABLE_PASSWORD, ACCEPTABLE_NUMBER);
        expect(dao.existsByUsername(email)).andReturn(true).anyTimes();
        replay(dao);

        //execute test
        try {
            userService.createNewUser(userDTO);
            fail();
        } catch(ServiceException e) {
            //expected
            assertEquals(UserService.UserServiceErrorCodes.USER_ALREADY_EXISTS, e.getErrorKey());
        }
    }

    @Test
    public void testCreateUserInvalidDataNotAccepted() {
        String[] invalidEmails = new String[] {
                "foo",
                "foo@bar",
                "@",
                "12234"
                //...
        };
        String[] invalidPhoneNumbers = new String[] {
                //invalid characters
                "a",
                "224a",
                //insufficient length
                "1"
                //...
        };
        for (String email : invalidEmails) {
            try {
                //test
                userService.createNewUser(createNewUserDTO(email, ACCEPTABLE_PASSWORD, ACCEPTABLE_NUMBER));
                fail("failed on accepting invalid email: "+ email);
            } catch (RuntimeException e) {
                e.printStackTrace();
                //expected
            } catch(ServiceException e) {
                e.printStackTrace();
                fail(); //unexpected
            }
        }

        for (String phoneNumber : invalidPhoneNumbers) {
            try {
                //test
                userService.createNewUser(createNewUserDTO(ACCEPTABLE_EMAIL, ACCEPTABLE_PASSWORD, phoneNumber));
                fail("failed on accepting invalid phone number: "+ phoneNumber);
            } catch (RuntimeException e) {
                e.printStackTrace();
                //expected
            } catch (ServiceException e) {
                e.printStackTrace();
                fail();//unexpected
            }
        }
    }

    @Test
    public void testUpdateUserWhenUserNotExists() {
        UserDTO user = createNewUserDTO(ACCEPTABLE_EMAIL, ACCEPTABLE_PASSWORD, ACCEPTABLE_NUMBER);
        user.setId(1L);
        expect(dao.findById(1L)).andReturn(Optional.empty());
        replay(dao);

        try {
            userService.updateUser(user);
            fail("expecting exception be thrown");
        } catch (ServiceException e) {
            assertEquals(UserService.UserServiceErrorCodes.getErrorCode(UserService.UserServiceErrorCodes.USER_NOT_EXISTS), e.getErrorKey());
        }
    }

    public void testUpdateUserNoPasswordRequired() {
        UserDTO user = createNewUserDTO(ACCEPTABLE_EMAIL, null, ACCEPTABLE_NUMBER);
        user.setId(1L);

        User dbUser = new User(null, ACCEPTABLE_PASSWORD, ACCEPTABLE_NUMBER, ACCEPTABLE_EMAIL);
        dbUser.setId(1L);
        expect(dao.findById(anyLong())).andReturn(Optional.of(dbUser));

    }

    private UserDTO createNewUserDTO(String email, String password, String phoneNumber) {
        UserDTO user = new UserDTO();
        user.setEmail(email);
        user.setPlainTextPassword(password);
        user.setPhoneNumber(phoneNumber);
        return user;
    }
}
