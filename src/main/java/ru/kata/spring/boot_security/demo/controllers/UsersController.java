package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.security.Principal;

@Controller
public class UsersController {

    @Autowired
    UserServiceImp userServiceImp;

    @GetMapping("/home")
    public String homeView()
    {
        return "home";
    }

    @GetMapping("/user")
    public String userView(Model model, Principal principal)
    {
        User user = userServiceImp.findByUsername(principal.getName());
        model.addAttribute("userModel", user);
        return "user";
    }

    @GetMapping("/")
    public String indexView() {
        return "redirect:/login";
    }
}
