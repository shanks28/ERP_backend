package com.example.ERP.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crm")
public class CRM {
    @GetMapping("/root")
    public String root(){
        return "hello man";
    }
}
