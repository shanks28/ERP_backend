package com.example.ERP.ServiceLayer;

import com.example.ERP.DTO.JobDTo;
import com.example.ERP.Models.Job;
import com.example.ERP.Repository.JobRepository;
import org.apache.coyote.Response;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final RedisService redisService;
    JobService(JobRepository jobRepository, RedisService redisService){
        this.redisService=redisService;
        this.jobRepository=jobRepository;
    }
    public List<Job> getAllRecords(){
        return jobRepository.findAll();
    }
    public Job findJob(Integer jobId){
        return jobRepository.findByJobId(jobId);
    }
    public ResponseEntity<Object> createJob(JobDTo.CRMEntryRequest request){
        try{
            String key="".concat(request.getCustomerName().toLowerCase()).concat(String.valueOf(request.getDate()).toLowerCase()).concat(request.getCategory().toLowerCase());
            String existingJob=(redisService.get(key));
            System.out.println(existingJob);
            if (existingJob!=null) {// this is a job that already exists with similar details
                Job job=new Job(Integer.parseInt(existingJob),request.getCustomerName(),request.getCategory(),request.getDate());
                jobRepository.save(job);// created job if exists and update database
            }
            else{// jobid does not exist in the redis container so need to generate a new one and update redis
                Integer lastJobId=jobRepository.findLatestJobId();
                Job job=new Job(lastJobId+1,request.getCustomerName(),request.getCategory(),request.getDate());
                jobRepository.save(job);
                System.out.println("job saved");
                redisService.set(key,String.valueOf(lastJobId+1));
            }
            return new ResponseEntity<>("Job created", HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>("Internal error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
