package com.example.ERP.Controller;

import com.example.ERP.Models.Job;
import com.example.ERP.ServiceLayer.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.example.ERP.DTO.*;
@RestController
@RequestMapping("/operations")
public class Operations {
    private final JobService jobService;
    public Operations(JobService crmService){
        this.jobService=crmService;
    }
    
    @PostMapping("/create-job")
    public ResponseEntity<Object> createJob(@RequestBody JobDTo.CRMEntryRequest request, Principal principal) {
        return jobService.createJob(request, principal);
    }
}
