package com.sandogh.sandogh.home;

import com.sandogh.sandogh.base.exceptions.ServiceErrorHelper;
import com.sandogh.sandogh.base.exceptions.ServiceException;
import com.sandogh.sandogh.base.utils.PasswordEncryptionUtils;
import com.sandogh.sandogh.users.entity.User;
import com.sandogh.sandogh.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * @author Ehsan Soleimani (esoleimani@voipfuture.com)
 **/
@Controller
public class HomePageController {
    @Autowired
    UserService userService;
    @Autowired
    private ServiceErrorHelper serviceErrorHelper;

    @GetMapping("/")
    public String showIndex(Model model) {
        return "index";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("userlist", userService.getAllUser());
        return "users_managment";
    }

    @GetMapping("/showNewUserForm")
    public String showNewUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "newuserform";
    }

    @GetMapping("/addnewuser")
    public String signUpPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "newuserform";
    }

    @PostMapping("/addnewuser")
    public String postSignUp(@Valid @ModelAttribute User user, BindingResult bindingResult,
                             RedirectAttributes attributes) throws ServiceException {
        if (bindingResult.hasErrors()) {
            return "newuserform";
        }
        final User userEntity;
        try {
            userEntity = userService.createNewUser(user.getUsername(), user.getPassword(), user.getEmail(), user.getPhoneNumber());
        } catch(ServiceException e) {
            if (serviceErrorHelper.handleError(e, UserService.UserServiceErrorMessages.USER_ALREADY_EXISTS, new ServiceErrorHelper.ServiceErrorConsumer() {
                @Override
                public void handle(String errorCode, Object[] params) {
                    attributes.addFlashAttribute("user_exists_message", "email already exists !");
                    attributes.addFlashAttribute("warning_class", "alert alert-warning");
                }
            })) {
                return "redirect:/addnewuser";
            }
        }
        attributes.addFlashAttribute("success_message", "user created successfully !");
        attributes.addFlashAttribute("success_class", "alert alert-success");
        return "redirect:/addnewuser";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id") long id) {
        userService.getDeleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "update_user";
    }

    @PostMapping("/updateuser")
    public String updateUser(@Valid @ModelAttribute User user, BindingResult bindingResult,
                             RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return "update_user";
        }
        userService.getDeleteUser(user.getId());
        User userEntity = userService.createNewUser(user.getUsername(), user.getPassword(), user.getEmail(), user.getPhoneNumber(), user.getToken(), user.isActive(), user.getRolelist());
        attributes.addFlashAttribute("success_message", "user updated successfully !");
        attributes.addFlashAttribute("success_class", "alert alert-success");
        return "redirect:/updateuser";
    }

    @GetMapping("/updateuser")
    public String updatePage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "update_user";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/activation/{token}")
    public String activeUserByToken(@PathVariable("token") String token) {
        token = token.replace("'", "");

        if (userService.userByTokenExists(token)) {
            userService.activeUser(token);
        }
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String getProfilePage() {
        return "profile";
    }

}
