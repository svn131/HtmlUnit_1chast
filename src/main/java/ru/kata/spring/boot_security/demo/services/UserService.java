package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.Collection;
import java.util.List;

public interface UserService extends UserDetailsService {

    User findByUsername(String username);

    UserDetails loadUserByUsername(String username);

    Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles);

    void createRole(Role role);

    Role findRoleByName(String roleName);

    void createUser(User user);

    List<User> findAll();

    void update(User user);

    User save(User user);

    void deleteUser(User user);

    User findById(Long id);

    Role getOneRole (Long roleId);

    List<Role> findAllRole ();
}
