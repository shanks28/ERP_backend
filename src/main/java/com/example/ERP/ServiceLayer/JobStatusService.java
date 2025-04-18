package com.example.ERP.ServiceLayer;

import org.springframework.stereotype.Service;
import com.example.ERP.Repository.jobStatusRepository;
import com.example.ERP.Repository.UserRepository;
import com.example.ERP.ServiceLayer.RedisService;

import jakarta.transaction.Transactional;

import com.example.ERP.DTO.JobStatusUpdateDTO;
import com.example.ERP.Models.JobStatus;
import org.springframework.http.ResponseEntity;
import java.util.*;
import java.util.Collections;
import java.util.Collection;
import org.springframework.http.HttpStatus;
@Service
public class JobStatusService {
    private final jobStatusRepository jobStatusRepository;
    private final UserRepository userRepository;
    private final RedisService redisService;
    private final EmailService emailService;
    JobStatusService(EmailService emailService,jobStatusRepository jobStatusRepository, UserRepository userRepository,RedisService redisService) {
        this.jobStatusRepository = jobStatusRepository;
        this.userRepository = userRepository;
        this.redisService=redisService;
        this.emailService=emailService;
    }
    public ResponseEntity<?> getJobStatus(int slNo) {
        try{
            JobStatus jobStatus=jobStatusRepository.findByJobSlNo(slNo);
            if(jobStatus==null){
                return new ResponseEntity<>("Job not Found",HttpStatus.NOT_FOUND);
            }
            Map<String,Object> response=new HashMap<>();
            response.put("CRM Status",jobStatus.getCrmStatus());
            response.put("BILLING Status",jobStatus.getBillingStatus());
            response.put("OPERATIONS Status",jobStatus.getOperationsStatus());
            response.put("Slno",jobStatus.getJob().getSlNo());
            response.put("JobID",jobStatus.getJob().getJobId());
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    @Transactional
    public ResponseEntity<?>updateJobStatus(int slNo,JobStatusUpdateDTO jobStatusUpdateDTO){
        try{
            JobStatus jobStatus=jobStatusRepository.findByJobSlNo(slNo);
            if (jobStatus==null){
                return new ResponseEntity<>("Job not Found",HttpStatus.NOT_FOUND);
            }
            if (jobStatusUpdateDTO.getCrmStatus()!=null){
                jobStatus.setCrmStatus(jobStatusUpdateDTO.getCrmStatus());
            }
            if(jobStatusUpdateDTO.getBillingStatus()!=null){
                jobStatus.setBillingStatus(jobStatusUpdateDTO.getBillingStatus());
            }
            if(jobStatusUpdateDTO.getOperationsStatus()!=null){
                jobStatus.setOperationsStatus(jobStatusUpdateDTO.getOperationsStatus()); 
            }
            jobStatusRepository.save(jobStatus);
            boolean isCompleted=false;
            if(jobStatus.getCrmStatus().equalsIgnoreCase("Completed")
            || jobStatus.getBillingStatus().equalsIgnoreCase("Completed")
            || jobStatus.getOperationsStatus().equalsIgnoreCase("Completed")){
                isCompleted=true;
            }
            if(isCompleted){
                //send mail to the admins
                emailService.sendEmailNotification(jobStatus);//sends current job status

            }
            return new ResponseEntity<>("Job Updated",HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

}
