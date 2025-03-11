package com.example.ERP.Controller;

import com.example.ERP.Models.Job;
import com.example.ERP.ServiceLayer.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/job")
public class JobController { // this separate controller makes the job of the front end person easier
    // same entity is updated but different fields are updated by different roles
    private final JobService jobService;
    public JobController(JobService jobService){
        this.jobService=jobService;
    }
    @PatchMapping("/update-job")
    public ResponseEntity<String> updateJob(@RequestBody Map<String,Object> request, Principal principal){
        return jobService.updateJob(request,principal);
    }
    @GetMapping("/get-all-jobs")
    public List<Job> getAllJobs(){
        return jobService.getAllRecords();
    }
}
