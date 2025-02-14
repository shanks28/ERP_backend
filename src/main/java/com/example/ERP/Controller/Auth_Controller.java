package com.example.ERP.Controller;

import com.example.ERP.DTO.AuthDTO;
import com.example.ERP.Models.User;
import com.example.ERP.ServiceLayer.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class Auth_Controller {

    private final Auth authService;
    public Auth_Controller(Auth authService){
        this.authService=authService;
    }

    @GetMapping
    public String root(){
        return "Hello";
    }
    @PostMapping("/register")
    public AuthDTO.AuthResponse register(@RequestBody User details){
         return authService.register(details);
    }

}
