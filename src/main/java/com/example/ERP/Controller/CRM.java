package com.example.ERP.Controller;

import com.example.ERP.DTO.JobDTo;
import com.example.ERP.Models.Job;
import com.example.ERP.ServiceLayer.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/crm")
public class CRM {
    private final JobService jobService;
    public CRM(JobService jobService){
        this.jobService=jobService;
    }

    @GetMapping("/find-job/{jobId}")
    public Job getId(@PathVariable("jobId") Integer jobId) {
        return jobService.findJob(jobId);
    }
    @PostMapping("/create-job")
    public ResponseEntity<Object> createJob(@RequestBody JobDTo.CRMEntryRequest request,Principal principal){
        return jobService.createJob(request,principal);
    }

}
