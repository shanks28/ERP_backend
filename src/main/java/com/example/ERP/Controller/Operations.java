package com.example.ERP.Controller;

import com.example.ERP.Models.Job;
import com.example.ERP.ServiceLayer.JobService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/operations")
public class Operations {
    private final JobService crmService;
    public Operations(JobService crmService){
        this.crmService=crmService;
    }
    @GetMapping("/get-all-jobs")
    public List<Job> getAll(){
        return crmService.getAllRecords();
    }
    @GetMapping("/find-job/{jobId}")
    public Job getId(@PathVariable("jobId") Integer jobId){
        return crmService.findJob(jobId);
    }
}
