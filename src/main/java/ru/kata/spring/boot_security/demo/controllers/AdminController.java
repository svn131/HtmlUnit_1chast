package ru.kata.spring.boot_security.demo.controllers;

import lombok.extern.log4j.Log4j2;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Log4j2
@Controller
public class AdminController {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = (BCryptPasswordEncoder)passwordEncoder;
    }


    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        log.info("Admin page requested 1111111111111111111111111111111111111111111111111111111111111111111111111"+ model+ "22");
        List<User> userList = userService.findAll();
        model.addAttribute("users", userList);
        return "admin";
    }


    @GetMapping("/admin/create")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User());
        return "admin-create-user-form";
    }

    @PostMapping("/admin/create")
    public String createUser(@ModelAttribute("user") User user) {
        Set<Role> roles = new HashSet<>();
        Role userRole = userService.findRoleByName("ROLE_USER");
        roles.add(userRole);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Шифрование пароля
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "edit-user-form";
    }

    @PostMapping("/admin/edit")
    public String editUser(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.update(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteUser(@ModelAttribute("user") User user) {
        userService.deleteUser(user);
        return "redirect:/admin";
    }


}