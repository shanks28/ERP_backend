package com.example.ERP.ServiceLayer;

import com.example.ERP.DTO.AuthDTO;
import com.example.ERP.Models.User;
import com.example.ERP.Repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class Auth {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    Auth(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;

    }

    public String register(User details) {
        User existingUser = userRepository.findByUserName(details.getUserName());
        if (existingUser != null) {
            return new String("User Already Exists");
        }
        details.setPassword(passwordEncoder.encode(details.getPassword()));
        userRepository.save(details);
        return new String("Employee Registered Successfully");

    }

}
