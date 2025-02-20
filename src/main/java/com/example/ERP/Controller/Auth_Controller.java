package com.example.ERP.Controller;

import com.example.ERP.DTO.AuthDTO;
import com.example.ERP.Models.User;
import com.example.ERP.ServiceLayer.Auth;
import com.example.ERP.ServiceLayer.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatusCode;
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
        return emailService.sendMail(request.getEmail());
    }
    @GetMapping("/verify-otp")
    public HttpStatusCode verifyOtp(@RequestParam String email, @RequestParam String otp){
        return authService.verifyOtp(email,otp);
    }
    @GetMapping("/update-password")
    public HttpStatusCode resetPassword(@RequestParam String email,@RequestParam String new_password){
        return authService.resetPassword(email,new_password);
    }
}
