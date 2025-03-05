package com.example.ERP.Controller;

import com.example.ERP.Models.Job;
import com.example.ERP.ServiceLayer.CRMService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/crm")
public class CRM {
    private final CRMService crmService;
    CRM(CRMService crmService){
        this.crmService=crmService;
    }
    @GetMapping("/root")
    public String root(){
        return "hello man";
    }
    @GetMapping("/get-all")
    public List<Job> getAll(){
        return crmService.getAllRecords();
    }
}
