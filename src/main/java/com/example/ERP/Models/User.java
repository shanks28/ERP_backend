package com.example.ERP.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="Users")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column(name = "username",nullable = false,unique = true)
    private String userName;

    @Column(name="password",nullable = false)
    private String password;

    @Column(name="email",nullable = false,unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = true)
    private Integer OTP;

    @Column(nullable = true,name = "last_login")
    private String lastLogin;


}
