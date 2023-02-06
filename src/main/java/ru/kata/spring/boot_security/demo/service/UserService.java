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

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @PostConstruct
    void test() {

        Role userRole = new Role("ROLE_USER");
        Role adminRole = new Role("ROLE_ADMIN");

        Set<Role> userSet = new HashSet<Role>();
        Set<Role> adminSet = new HashSet<Role>();
        Set<Role> testSet = new HashSet<Role>();


        userSet.add(userRole);
        adminSet.add(adminRole);
        testSet.add(userRole);

        userRepository.save(new User("UserTommy", "Chong"
                , "user1", "$2a$12$M/EClJmn/C1UfPQrdLL2lu6Agi9IblOKHGKAOnPjA/lvGj4fPeWZe"
                , userSet));
        userRepository.save(new User("AdminCheech", "Marin"
                , "admin1", "$2a$12$z66K75ZA4pxARKVy5AFRNeekszW1Iy4O2OOoARjMtS.pMHq7Wrspe"
                , adminSet));

//
    }

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
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities( Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole_name()))
                .collect(Collectors.toList());
    }

    public User findByUsername (String username) {

        Optional<User> dbuser = userRepository.findByUsername(username);
        if (dbuser.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User '$s' not found!", username));
        }
        return userRepository.findByUsername(username).get();
    }

    public User findById (Long id) {
        Optional<User> dbuser = userRepository.findById(id);
        if (dbuser.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User with id='$s' not found!", id));
        }
        return dbuser.get();
    }

    public void removeById (Long id) {
        Optional<User> dbuser = userRepository.findById(id);
        if (dbuser.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User with id='$s' not found!", id));
        }

        User user = dbuser.get();
        user.getRoles().clear();
        userRepository.delete(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void saveUser(User user) throws Exception {
        user.setRoles(user.getRoles());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }
}
