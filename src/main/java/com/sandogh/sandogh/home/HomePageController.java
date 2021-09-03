package com.sandogh.sandogh.home;

import com.sandogh.sandogh.base.utils.PasswordEncryptionUtils;
import com.sandogh.sandogh.users.UserDTO;
import com.sandogh.sandogh.users.dao.UserDAO;
import com.sandogh.sandogh.users.entity.User;
import com.sandogh.sandogh.users.exceptions.UsernameAlreadyTakeException;
import com.sandogh.sandogh.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author Ehsan Soleimani (esoleimani@voipfuture.com)
 **/
@Controller
public class HomePageController {
    @Autowired
    UserService userService;
    @Autowired
    UserDAO userDAO;

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("showText", "Users List");
        model.addAttribute("userlist", userService.getAllUser());
        return "users_managment";
    }

    @GetMapping("/")
    public String showIndex(Model model) {
        model.addAttribute("showText", "Spring framwork");

        return "index";
    }

    @GetMapping("/showNewUserForm")
    public String showNewUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "newuserform";

    }

    @GetMapping("/addnewuser")
    public String signUpPage(Model model) {
        model.addAttribute("user", new User());
        return "newuserform";
    }

    @PostMapping("/addnewuser")
    public String postSignUp(@Valid @ModelAttribute UserDTO user, BindingResult bindingResult,
                             RedirectAttributes attributes) throws UsernameAlreadyTakeException {
        if (bindingResult.hasErrors()) {
            return "newuserform";
        }
        if (userService.userByEmailExists(user.getEmail())) {
            attributes.addFlashAttribute("user_exists_message", "email already exists !");
            attributes.addFlashAttribute("warning_class", "alert alert-warning");
            return "redirect:/addnewuser";
        }

//        PasswordEncryptionUtils passwordEncryptionUtils = new PasswordEncryptionUtils();
//        String password = passwordEncryptionUtils.getHashedPasswordAndSaltCombination(user.getPassword());
//        user.setPassword(password);
//
//        userDAO.save(user);
        User userEntity = userService.createNewUser(user.getUsername(), user.getPassword(), user.getEmail(), user.getPhoneNumber());
        attributes.addFlashAttribute("success_message", "user created successfully !");
        attributes.addFlashAttribute("success_class", "alert alert-success");
        return "redirect:/users";

    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        //get User from server
        User user = userService.getUserById(id);

        // set user
        model.addAttribute("user", user);
        return "update_user";

    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id") long id) {
        userService.getDeleteUser(id);
        return "redirect:/users";
    }

    @PostMapping("/updateuser")
    public String updateUser(@Valid @ModelAttribute User user, BindingResult bindingResult,
                             RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return "newuserform";
        }
        PasswordEncryptionUtils passwordEncryptionUtils = new PasswordEncryptionUtils();
        String password = passwordEncryptionUtils.getHashedPasswordAndSaltCombination(user.getPassword());
        user.setPassword(password);
        userDAO.save(user);
        attributes.addFlashAttribute("success_message", "user created successfully !");
        attributes.addFlashAttribute("success_class", "alert alert-success");
        return "redirect:/addnewuser";

    }


}
