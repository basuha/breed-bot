package com.basuha.breed_bot.controller;

import com.basuha.breed_bot.message.User;
import com.basuha.breed_bot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String main(){
        return "index";
    }

    @GetMapping("/register")
    public String registration() {
        return "register";
    }

    @PostMapping("/register")
    public String addUser(
            @RequestParam String username,
            @RequestParam String password,
            Model model
    ) {
        if (!userService.addUser(new User(1233L, username,password))) { //TODO: пофиксить
            model.addAttribute("usernameError", "User exists!");
            return "register";
        }
        return "redirect:login";
    }
}