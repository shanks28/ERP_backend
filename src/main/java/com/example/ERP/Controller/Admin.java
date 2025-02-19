package com.example.ERP.Controller;

import com.example.ERP.DTO.AuthDTO;
import com.example.ERP.Models.User;
import com.example.ERP.ServiceLayer.AdminService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class Admin {
    private final AdminService adminService;

    Admin(AdminService adminService){
        this.adminService=adminService;
    }

    @GetMapping("/reset")
    public String root(){
        return adminService.sendEmail();
    }
}
