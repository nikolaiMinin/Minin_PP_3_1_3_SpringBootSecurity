package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserDetailsService, UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    @Transactional // чтобы обойти LAZY загрузку
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> dbuser = userRepository.findByUsername(username);
        if (dbuser.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User '%s' not found!", username));
        }
        User user = dbuser.get();
        return new org.springframework.security.core.userdetails
                .User(user.getUsername(),user.getPassword(),mapRolesToAuthorities(user.getRoles()));
    }

    @Override
    public Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole_name()))
                .collect(Collectors.toList());
    }

    @Override
    public User findByUsername (String username) {

        Optional<User> dbuser = userRepository.findByUsername(username);
        if (dbuser.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User '$s' not found!", username));
        }
        return userRepository.findByUsername(username).get();
    }

    @Override
    public User findById (Long id) {
        Optional<User> dbuser = userRepository.findById(id);
        if (dbuser.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User with id='$s' not found!", id));
        }
        return dbuser.get();
    }

    @Override
    public void removeById (Long id) {
        Optional<User> dbuser = userRepository.findById(id);
        if (dbuser.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User with id='$s' not found!", id));
        }

        User user = dbuser.get();
        user.getRoles().clear();
        userRepository.delete(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) throws Exception {
        user.setRoles(user.getRoles());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void editUser (User user) {
        user.setRoles(user.getRoles());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public boolean svUser(User user) {
        Optional<User> userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB.isPresent()) {
            return false;
        }
        roleRepository.saveAll(user.getRoles());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.saveAndFlush(user);
        return true;
    }

}
