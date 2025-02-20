package com.example.ERP.Controller;

import com.example.ERP.DTO.AuthDTO;
import com.example.ERP.Models.User;
import com.example.ERP.ServiceLayer.Auth;
import com.example.ERP.ServiceLayer.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class Auth_Controller {

    private final Auth authService;
    private final EmailService emailService;
    public Auth_Controller(Auth authService,EmailService emailService){
        this.authService=authService;
        this.emailService=emailService;
    }

    @PostMapping("/register")
    public String register(@RequestBody User details){
        return authService.register(details);
    }
    @PostMapping("/login")
    public String login(@RequestBody AuthDTO.LoginRequest request, HttpServletRequest httpRequest) {
        return authService.login(request,httpRequest);
    }
    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody AuthDTO.ResetPassword request) {
        try {
            String otp = "12445";
            emailService.sendMail(request.getEmail(), otp);
            return "OTP SENT";
        } catch (Exception e) {
            return e.toString();
        }
    }
}
