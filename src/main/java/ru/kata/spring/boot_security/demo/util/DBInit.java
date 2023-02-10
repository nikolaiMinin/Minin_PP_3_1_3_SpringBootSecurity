package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class DBInit {

    @Autowired
    UserServiceImp userServiceImp;

    @PostConstruct
    void test() {

        Role userRole = new Role("ROLE_USER");
        Role adminRole = new Role("ROLE_ADMIN");
        Role testRole = new Role("ROLE_TEST");

        Set<Role> userSet = new HashSet<Role>();
        Set<Role> adminSet = new HashSet<Role>();
        Set<Role> testSet = new HashSet<Role>();


        userSet.add(userRole);
        adminSet.add(adminRole);
        adminSet.add(userRole);

        testSet.add(testRole);

        userServiceImp.svUser(new User("UserTommy", "Chong"
                , "user", "1234"
                , userSet));
        userServiceImp.svUser(new User("AdminCheech", "Marin"
                , "admin", "100"
                , adminSet));
        userServiceImp.svUser(new User("test01", "test01"
                , "test", "test"
                , testSet));
    }

}
