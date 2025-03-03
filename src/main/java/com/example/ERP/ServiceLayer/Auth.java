package com.example.ERP.ServiceLayer;

import com.example.ERP.DTO.AuthDTO;
import com.example.ERP.Models.User;
import com.example.ERP.Repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class Auth {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    Auth(UserRepository userRepository,PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager) {
        this.authenticationManager=authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;

    }

    public Integer register(User details) {
        User existingUser = userRepository.findByUserName(details.getUserName());
        if (existingUser != null) {
            return ResponseEntity.badRequest().build().getStatusCode().value();// 4XX
        }
        details.setPassword(passwordEncoder.encode(details.getPassword()));
        userRepository.save(details);
        return ResponseEntity.ok().build().getStatusCode().value(); //2XX

    }
    public Integer login(AuthDTO.LoginRequest request, HttpServletRequest httpRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            User obj=userRepository.findByUserName(request.getUsername());
            obj.setLastLogin(LocalDateTime.now());
            userRepository.save(obj);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
            HttpSession session = httpRequest.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext); //used for RBAC later
            session.setAttribute("user", request.getUsername());
            return ResponseEntity.ok().build().getStatusCode().value();// 200
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build().getStatusCode().value();//5XX
        }
    }
    public Integer verifyOtp(String email, String otp){
        User obj=userRepository.findByEmail(email);
        if (obj.getOTP().equals(Integer.parseInt(otp))){
            return ResponseEntity.ok().build().getStatusCode().value();
        }
        return ResponseEntity.badRequest().build().getStatusCode().value();
    }
    public Integer resetPassword(String email,String new_password){
        User obj=userRepository.findByEmail(email);
        obj.setPassword(passwordEncoder.encode(new_password));
        obj.setOTP(null);
        userRepository.save(obj);
        return ResponseEntity.ok().build().getStatusCode().value();
    }


}
