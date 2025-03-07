package com.example.ERP.Controller;

import com.example.ERP.DTO.JobDTo;
import com.example.ERP.Models.Job;
import com.example.ERP.ServiceLayer.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crm")
public class CRM {
    private final JobService jobService;
    public CRM(JobService jobService){
        this.jobService=jobService;
    }
    @GetMapping("/root")
    public String root(){
        return "hello man";
    }
    @GetMapping("/get-all-jobs")
    public List<Job> getAll(){
        return jobService.getAllRecords();
    }
    @GetMapping("/find-job/{jobId}")
    public Job getId(@PathVariable("jobId") Integer jobId) {
        return jobService.findJob(jobId);
    }
    @PostMapping("/create-job")
    public ResponseEntity<Object> createJob(@RequestBody JobDTo.CRMEntryRequest request){
        return jobService.createJob(request);
    }

}
