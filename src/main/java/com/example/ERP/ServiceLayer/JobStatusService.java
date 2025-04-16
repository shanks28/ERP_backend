package com.example.ERP.ServiceLayer;

import org.springframework.stereotype.Service;
import com.example.ERP.Repository.jobStatusRepository;
import com.example.ERP.Repository.UserRepository;
import com.example.ERP.ServiceLayer.RedisService;
import com.example.ERP.Models.JobStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
@Service
public class JobStatusService {
    private final jobStatusRepository jobStatusRepository;
    private final UserRepository userRepository;
    private final RedisService redisService;
    JobStatusService(jobStatusRepository jobStatusRepository, UserRepository userRepository,RedisService redisService) {
        this.jobStatusRepository = jobStatusRepository;
        this.userRepository = userRepository;
        this.redisService=redisService;
    }
    public ResponseEntity<?> getJobStatus(int slNo) {
        try{
            JobStatus jobStatus=jobStatusRepository.findByJobSlNo(slNo);
            if(jobStatus==null){
                return new ResponseEntity<>("Job not Found",HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(jobStatus.toString(),HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    

}
