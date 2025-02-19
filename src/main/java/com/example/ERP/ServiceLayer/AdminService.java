package com.example.ERP.ServiceLayer;

import com.example.ERP.Models.User;
import com.example.ERP.Repository.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final Dotenv dotenv;

    public AdminService(UserRepository userRepository,Dotenv dotenv){
        this.userRepository=userRepository;
        this.dotenv=dotenv;
    }

    public String sendEmail(){
        return dotenv.get("HOST");

    }
}
