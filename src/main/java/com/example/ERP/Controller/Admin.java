package com.example.ERP.Controller;

import com.example.ERP.DTO.AuthDTO;
import com.example.ERP.Models.User;
import com.example.ERP.ServiceLayer.AdminService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.ERP.ServiceLayer.Auth;
@RestController
@RequestMapping("/admin")
public class Admin {
    private final AdminService adminService;
    private final Auth authService;

    Admin(AdminService adminService,Auth authService){
        this.adminService=adminService;
        this.authService=authService;
    }

    // @GetMapping("/reset")
    // public String root(){
    //     return adminService.sendEmail();
    // }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User details){
        return authService.register(details);
    }
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        return adminService.getAllUsers();
    }
}
