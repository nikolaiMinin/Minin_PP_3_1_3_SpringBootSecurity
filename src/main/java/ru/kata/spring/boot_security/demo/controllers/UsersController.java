package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsersController {
    @GetMapping("/hello")
    public String openIndex() {
        return "hello";
    }

}
