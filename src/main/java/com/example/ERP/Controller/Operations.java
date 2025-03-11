package com.example.ERP.Controller;

import com.example.ERP.Models.Job;
import com.example.ERP.ServiceLayer.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/operations")
public class Operations {
    private final JobService jobService;
    public Operations(JobService crmService){
        this.jobService=crmService;
    }
    @GetMapping("/find-job/{jobId}")
    public Job getId(@PathVariable("jobId") Integer jobId){
        return jobService.findJob(jobId);
    }
}
