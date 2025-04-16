package com.example.ERP.Controller;
import com.example.ERP.ServiceLayer.JobStatusService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
@RestController
@RequestMapping("/jobStatus")
public class JobStatusController {
    private final JobStatusService jobStatusService;
    JobStatusController(JobStatusService jobStatusService){
        this.jobStatusService=jobStatusService;
    }
    @GetMapping("/get-job/{slNo}")
    public ResponseEntity<?> getJobStatus(@PathVariable int slNo){
        return jobStatusService.getJobStatus(slNo);
    }

}
