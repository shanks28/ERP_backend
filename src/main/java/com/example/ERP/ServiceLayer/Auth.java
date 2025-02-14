package com.example.ERP.ServiceLayer;

import com.example.ERP.DTO.AuthDTO;
import com.example.ERP.Models.User;
import com.example.ERP.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
@Service
public class Auth {

    private final UserRepository userRepository;

    Auth(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthDTO.AuthResponse register(User details) {
        User existingUser = userRepository.findByUserName(details.getUserName());

        if (existingUser != null) {
            throw new RuntimeException("User already exists!");
        }
        userRepository.save(details);

        return new AuthDTO.AuthResponse(details.getUserName(), details.getRole());

    }
}
