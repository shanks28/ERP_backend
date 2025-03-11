package com.example.ERP.ServiceLayer;

import com.example.ERP.DTO.JobDTo;
import com.example.ERP.Models.Job;
import com.example.ERP.Repository.JobRepository;
import org.apache.coyote.Response;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                Job job=new Job(Integer.parseInt(existingJob),request.getCustomerName(),request.getCategory(),request.getDate(),request.getSellingPrice());
                jobRepository.save(job);// created job if exists and update database
            }
            else{// jobid does not exist in the redis container so need to generate a new one and update redis
                Integer lastJobId=jobRepository.findLatestJobId();
                Job job=new Job(lastJobId+1,request.getCustomerName(),request.getCategory(),request.getDate(),request.getSellingPrice());
                jobRepository.save(job);
                System.out.println("job saved");
                redisService.set(key,String.valueOf(lastJobId+1));
            }
            return new ResponseEntity<>("Job created", HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>("Internal error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<String> updateJob(Map<String,Object> request, Principal principal){
        try{
            Authentication auth= SecurityContextHolder.getContext().getAuthentication();
            List<String> roles=auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();
            List<String> unauthorizedFields=new ArrayList<>();
            List<String> crmAllowedFields=List.of("jobId","customerName","jobDate","category","sellingPrice");
            List<String> operationsAllowedFields=List.of("jobParticulars","jobReference","boeSbNo",
                    "boeSbDate","arrivalDate","tentativeClosureDate","closedDate","sellingPrice","billingStatus","invoiceNo",
                    "invoiceDate","courier_tracking_no","payment_status","remarks",
                    "apekshaInvoiceNo","action");
            Integer slNo=(Integer) request.get("slNo");
            if (slNo==null){
                return new ResponseEntity<>("slNo not valid",HttpStatus.BAD_REQUEST);
            }
            Job existingJob=jobRepository.findByslNo(slNo);
            for(String key:request.keySet()){
                if(key.equals("slNo")){
                    continue;
                }
                if(roles.contains("ROLE_CRM")){
                    if (!crmAllowedFields.contains(key)) {
                        unauthorizedFields.add(key);
                    }
                }
            }
            if(!unauthorizedFields.isEmpty()){
                return new ResponseEntity<>("Unauthorized updates:"+String.join(",",unauthorizedFields),HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("Job Updated",HttpStatus.OK);
        }catch (Exception E){
            return new ResponseEntity<>(E.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
