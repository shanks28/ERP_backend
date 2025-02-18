package com.example.ERP.Controller;

import com.example.ERP.DTO.AuthDTO;
import com.example.ERP.Models.User;
import com.example.ERP.ServiceLayer.Auth;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class Auth_Controller {

    private final Auth authService;
    private final AuthenticationManager authenticationManager;
    public Auth_Controller(Auth authService,AuthenticationManager authenticationManager){
        this.authService=authService;
        this.authenticationManager=authenticationManager;
    }

    @GetMapping
    public String root(){
        return "Hello";
    }

    @PostMapping("/register")
    public String register(@RequestBody User details){
        return authService.register(details);
    }
    @PostMapping("login")
    public String login(
            @RequestBody AuthDTO.LoginRequest request,
            HttpServletRequest httpRequest
    ) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
            HttpSession session = httpRequest.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            session.setAttribute("user", request.getUsername());
            return "Login Successful";
        } catch (Exception e) {
            return "Invalid Username/Password";
        }
    }

}
