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
        return "users";
    }

    @GetMapping("/users/new")
    public String showNewForm(Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Add New User");
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(@Valid @ModelAttribute User user, BindingResult bindingResult,
                           RedirectAttributes attributes) throws ServiceException {
        if (bindingResult.hasErrors()) {
            return "user_form";
        }
        final User userEntity;
        try {
            userEntity = userService.createNewUser(user);
        } catch(ServiceException e) {
            if (serviceErrorHelper.handleError(e, UserService.UserServiceErrorMessages.USER_ALREADY_EXISTS, new ServiceErrorHelper.ServiceErrorConsumer() {
                @Override
                public void handle(String errorCode, Object[] params) {
                    attributes.addFlashAttribute("success_message", "user created successfully !");
                    attributes.addFlashAttribute("success_class", "alert alert-success");
                }
            })) {
                return "redirect:/addnewuser";
            }
        }
        attributes.addFlashAttribute("success_message", "user created successfully !");
        attributes.addFlashAttribute("success_class", "alert alert-success");
        return "redirect:/users/new";


    }

    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable(value = "id") long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getUserById(id);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Update User(ID : " + id + ")");
            return "user_form";

        } catch (ServiceException e) {
            serviceErrorHelper.handleError(e, UserService.UserServiceErrorMessages.USER_NOT_FOUND, new ServiceErrorHelper.ServiceErrorConsumer() {
                @Override
                public void handle(String errorCode, Object[] params) {
                    redirectAttributes.addFlashAttribute("success_message", "user updated successfully !");
                }
            });
            return "redirect:/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") long id) {

        userService.getDeleteUser(id);
        return "redirect:/users";
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
