package com.example.ERP.Controller;

import com.example.ERP.DTO.AuthDTO;
import com.example.ERP.Models.User;
import com.example.ERP.ServiceLayer.Auth;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class Auth_Controller {

    private final Auth authService;
    public Auth_Controller(Auth authService){
        this.authService=authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody User details){
        return authService.register(details);
    }
    @PostMapping("/login")
    public String login(@RequestBody AuthDTO.LoginRequest request, HttpServletRequest httpRequest) {
        return authService.login(request,httpRequest);
    }
}
