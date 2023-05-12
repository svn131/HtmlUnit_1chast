package ru.kata.spring.boot_security.demo.init;

import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import javax.transaction.Transactional;
import java.util.*;


@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private boolean alreadySetup = false;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(PasswordEncoder passwordEncoder, UserService userService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_USER");

        createUserIfNotFound("user", "1111", "john.doe@example.com", Arrays.asList("ROLE_USER"));
        createUserIfNotFound("admin", "1111", "admin@example.com", Arrays.asList("ROLE_ADMIN"));

        alreadySetup = true;
    }

    @Transactional
    private void createRoleIfNotFound(String name) {
        Role role = userService.findRoleByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            userService.createRole(role);
        }
    }

    @Transactional
    private void createUserIfNotFound(String username, String password, String email, List<String> roles) {
        User user = userService.findByUsername(username);
        if (user == null) {
            user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            Set<Role> userRoles = new HashSet<>();
            for (String roleName : roles) {
                Role role = userService.findRoleByName(roleName);
                if (role != null) {
                    userRoles.add(role);
                }
            }
            user.setRoles(userRoles);
            userService.createUser(user);
        }
    }
}