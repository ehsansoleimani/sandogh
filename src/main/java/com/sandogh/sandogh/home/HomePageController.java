package com.sandogh.sandogh.home;


import com.sandogh.sandogh.users.entity.User;
import com.sandogh.sandogh.users.exceptions.UsernameAlreadyTakeException;
import com.sandogh.sandogh.users.exceptions.userNotFoundException;
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
                           RedirectAttributes attributes) throws UsernameAlreadyTakeException {
        if (bindingResult.hasErrors()) {
            return "user_form";
        }
        if (userService.userByEmailExists(user.getEmail())) {
            String checkEmail = user.getEmail();
            User checkuser = userService.getEmail(checkEmail);
            if (checkuser.getId() == user.getId()) {
                User userEntity = userService.createUser(user);
                attributes.addFlashAttribute("success_message", "user created successfully !");
                attributes.addFlashAttribute("success_class", "alert alert-success");
                return "redirect:/users/new";
            }
            attributes.addFlashAttribute("user_exists_message", "email already exists !");
            attributes.addFlashAttribute("warning_class", "alert alert-warning");
            return "redirect:/users/new";
        }

        User userEntity = userService.createUser(user);
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

        } catch (userNotFoundException e) {
            redirectAttributes.addFlashAttribute("success_message", "user updated successfully !");
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









