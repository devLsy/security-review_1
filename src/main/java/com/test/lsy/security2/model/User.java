package com.test.lsy.security2.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "user_info")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role;

    private String email;
    private String provider;
    private String prodiverId;

    @CreationTimestamp
    private Timestamp loginDate;

    @Builder
    public User(String username, String password, String role, String email, String provider, String prodiverId ,Timestamp loginDate) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.provider = provider;
        this.prodiverId = prodiverId;
        this.loginDate = loginDate;
    }
}
