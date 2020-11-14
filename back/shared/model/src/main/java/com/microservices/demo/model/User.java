package com.microservices.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Data
//@Entity
@NoArgsConstructor
@Table
public class User {

//    @GeneratedValue
    @Id
    private Long id;

    private String username;

    private String password;
    private String email;
    private String role;

    public User(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
