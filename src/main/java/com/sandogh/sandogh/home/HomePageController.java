package com.sandogh.sandogh.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Ehsan Soleimani (esoleimani@voipfuture.com)
 **/
@Controller
public class HomePageController {

    @GetMapping("/")
    public String homePage(Model model) {
        return "index";
    }
}
