package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImp userServiceImp;
    private final RoleServiceImp roleServiceImp;

    @Autowired
    public AdminController(UserServiceImp userServiceImp, RoleServiceImp roleServiceImp) {
        this.userServiceImp = userServiceImp;
        this.roleServiceImp = roleServiceImp;
    }

    @GetMapping()
    public String adminView(Model model) {
        model.addAttribute("users", userServiceImp.getAllUsers());
        return "admin";
    }

    @GetMapping("/new")
    public String newView(Model model)
    {
        User user = new User();
        model.addAttribute("user",user);
        model.addAttribute("listRoles", roleServiceImp.listRoles());
        return "new";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user) throws Exception {
        userServiceImp.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
    public String removeUserById(@PathVariable("id") Long id) {
        userServiceImp.removeById(id);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userServiceImp.findById(id));
        model.addAttribute("listRoles", roleServiceImp.listRoles());
        return "edit";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") User user) {
        userServiceImp.editUser(user);
        return "redirect:/admin";
    }

}
