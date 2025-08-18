package com.spring.notes.controllers;

import com.spring.notes.entities.User;
import com.spring.notes.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping("/")
    public String homePage(){
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String registrationPage(){
        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboardPage(){
        return "dashboard";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String confirmPassword,
                               Model model){

        if(!password.equals(confirmPassword)){
            model.addAttribute("error", "Password does not match!");
            return "register";
        }
        userService.registerUser(email,username,password);
        model.addAttribute("success","Registration successful! Please Login");
        return "login";

    }



}
