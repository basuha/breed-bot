package com.basuha.breed_bot.controller;

import com.basuha.breed_bot.message.User;
import com.basuha.breed_bot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

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
            @Valid User user,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("user",user);
            return "register";
        } else {
            if (!userService.addUser(user)) {
                model.addAttribute("usernameError", "User exists!");
                return "register";
            }
        }
        return "redirect:login";
    }
}