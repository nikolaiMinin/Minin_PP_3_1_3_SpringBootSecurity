package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.GrantedAuthority;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

public interface UserService {
    Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles);

    User findByUsername(String username);

    User findById(Long id);

    void removeById(Long id);

    List<User> getAllUsers();

    @Transactional
    void saveUser(User user) throws Exception;

    @Transactional
    void editUser(User user);

    @Transactional
    boolean svUser(User user);
}
