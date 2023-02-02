package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    void test() {


        Role testRole = new Role("Boss");
        Set<Role> testSet = new HashSet<Role>();
        testSet.add(testRole);

        userRepository.save(new User("John", "Malcovich", "johnm", "1234", testSet));
    }
}
