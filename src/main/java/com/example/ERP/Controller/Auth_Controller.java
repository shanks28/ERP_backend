package com.example.ERP.Controller;

import com.example.ERP.DTO.AuthDTO;
import com.example.ERP.Models.User;
import com.example.ERP.ServiceLayer.Auth;
import com.example.ERP.ServiceLayer.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import jakarta.websocket.server.PathParam;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class Auth_Controller {

    private final Auth authService;
    private final EmailService emailService;
    public Auth_Controller(Auth authService,EmailService emailService){
        this.authService=authService;
        this.emailService=emailService;
    }
    @GetMapping
    public Integer root(){
        return ResponseEntity.ok().build().getStatusCode().value();
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User details){
        return authService.register(details);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthDTO.LoginRequest request, HttpServletRequest httpRequest) {
        return authService.login(request,httpRequest);// ROLE and email to be sent to the front end
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody AuthDTO.ResetPassword request) {
        return emailService.sendMail(request.getEmail());
    }
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody Map<String,Object> request){
        return authService.verifyOtp((String)request.get("email"),(String)request.get("otp"));
    }
    @PostMapping("/update-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String,String> request){
        return authService.resetPassword(request.get("email"),request.get("new_password"));
    }
}
