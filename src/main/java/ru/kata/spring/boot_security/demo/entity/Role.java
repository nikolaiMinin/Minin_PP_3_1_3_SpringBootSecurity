package ru.kata.spring.boot_security.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "roles_table")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long role_id;

    @Column(name = "role_name")
    private String role_name;

    public Role() {
    }

    public Role(String role_name) {
        this.role_name = role_name;
    }

    public Long getId() {
        return role_id;
    }

    public void setId(Long id) {
        this.role_id = id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
}
