package com.example.ERP.ServiceLayer;

import com.example.ERP.DTO.JobDTo;
import com.example.ERP.Models.Job;
import com.example.ERP.Repository.JobRepository;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @Transactional
    public ResponseEntity<Object> createJob(JobDTo.CRMEntryRequest request,Principal principal){
        try{
            System.out.println("Createjob");
            String key="".concat(request.getCustomerName().toLowerCase()).concat(String.valueOf(request.getDate())).concat(request.getCategory().toLowerCase());
            System.out.println(key);
            String existingJob=(redisService.get(key));
            System.out.println(existingJob);
            if (existingJob!=null) {// this is a job that already exists with similar details
                Job job=new Job(Integer.parseInt(existingJob),request.getCustomerName(),request.getCategory(),request.getDate(),request.getSellingPrice());
                job.setUpdatedBy(principal.getName());
                jobRepository.save(job);// created job if exists and update database
            }
            else{// jobid does not exist in the redis container so need to generate a new one and update redis
                Integer lastJobId=jobRepository.findLatestJobId();
                Job job=new Job(lastJobId+1,request.getCustomerName(),request.getCategory(),request.getDate(),request.getSellingPrice());
                job.setUpdatedBy(principal.getName());
                jobRepository.save(job);
                redisService.set(key,String.valueOf(lastJobId+1));
            }
            return new ResponseEntity<>("Job created", HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private LocalDate parseDate(Object value) {
        if (value instanceof String) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return LocalDate.parse((String) value, formatter);
        } else if (value instanceof LocalDate) {
            return (LocalDate) value;
        }
        throw new IllegalArgumentException("Invalid date format");
    }
    @Transactional
    public ResponseEntity<String> updateJob(Map<String,Object> request, Principal principal){
        try{
            Authentication auth= SecurityContextHolder.getContext().getAuthentication();
            List<String> roles=auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();
            List<String> unauthorizedFields=new ArrayList<>();
            // this is to dynamically update all fields if any changes we can just add to list
            List<String> crmAllowedFields=List.of("jobId","customerName","jobDate","category","sellingPrice");
            List<String> operationsAllowedFields=List.of("jobParticulars","jobReference","boeSbNo",
                    "boeSbDate","arrivalDate","tentativeClosureDate","closedDate","billingStatus","invoiceNo",
                    "invoiceDate","courier_tracking_no","payment_status","remarks",
                    "apekshaInvoiceNo","action");
            Integer slNo=(Integer) request.get("slNo");
            if (slNo==null){
                return new ResponseEntity<>("slNo not valid",HttpStatus.BAD_REQUEST);
            }
            Job existingJob=jobRepository.findByslNo(slNo);
            for(String key:request.keySet()) {
                if (key.equals("slNo")) {
                    continue;
                }
                if (roles.contains("ROLE_CRM")) {
                    if (!crmAllowedFields.contains(key)) {
                        unauthorizedFields.add(key);
                    }
                } else if (roles.contains("ROLE_OPERATIONS")) {
                    if (!operationsAllowedFields.contains(key)) {
                        unauthorizedFields.add(key);
                    }
                }
                if (!unauthorizedFields.isEmpty()) {// if it is no empty there has been a restricted update
                    return new ResponseEntity<>("Unauthorized updates:" + String.join(",", unauthorizedFields), HttpStatus.BAD_REQUEST);
                }

            }
            // if reached here it means the body is correct
            if (roles.contains("ROLE_CRM")) {
                if (request.get("jobId") != null) {
                    existingJob.setJobId((Integer) request.get("jobId"));
                }
                if (request.get("customerName") != null) {
                    existingJob.setCustomerName((String) request.get("customerName"));
                }
                if (request.get("jobDate") != null) {
                    String date=(String)request.get("jobDate");
                    DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate localDate=LocalDate.parse(date,formatter);
                    existingJob.setJobDate(localDate);
                }
                if (request.get("category") != null) {
                    existingJob.setCategory((String) request.get("category"));
                }
                if (request.get("sellingPrice") != null) {
                    existingJob.setSellingPrice((Integer) request.get("sellingPrice")); // Assuming it's a Double
                }
                if(request.get("remarks")!=null){
                    String existingRemarks= existingJob.getRemarks();
                    String newRemarks=(String)request.get("remarks");
                    existingJob.setRemarks(existingRemarks.concat(newRemarks));
                }
            }
            else if (roles.contains("ROLE_OPERATIONS")) {
                // Update Operations-allowed fields
                if (request.get("jobParticulars") != null) {
                    existingJob.setJobParticulars((String) request.get("jobParticulars"));
                }
                if (request.get("jobReference") != null) {
                    existingJob.setJobReference((String) request.get("jobReference"));
                }
                if (request.get("boeSbNo") != null) {
                    existingJob.setBoeSbNo((String) request.get("boeSbNo"));
                }
                if (request.get("boeSbDate") != null) {
                    existingJob.setBoeSbDate((LocalDate) request.get("boeSbDate"));
                }
                if (request.get("arrivalDate") != null) {
                    existingJob.setArrivalDate((LocalDate) request.get("arrivalDate"));
                }
                if (request.get("tentativeClosureDate") != null) {
                    existingJob.setTentativeClosureDate((LocalDate) request.get("tentativeClosureDate"));
                }
                if (request.get("closedDate") != null) {
                    existingJob.setClosedDate((LocalDate) request.get("closedDate"));
                }
                if (request.get("billingStatus") != null) {
                    existingJob.setBillingStatus((String) request.get("billingStatus"));
                }
                if (request.get("invoiceNo") != null) {
                    existingJob.setInvoiceNo((String) request.get("invoiceNo"));
                }
                if (request.get("invoiceDate") != null) {
                    existingJob.setInvoiceDate((LocalDate) request.get("invoiceDate"));
                }
                if (request.get("courier_tracking_no") != null) {
                    existingJob.setCourierTrackingNo((String) request.get("courier_tracking_no"));
                }
                if (request.get("payment_status") != null) {
                    existingJob.setPaymentStatus((String) request.get("payment_status"));
                }
                if (request.get("remarks") != null) {
                    String existingRemarks= existingJob.getRemarks();
                    String newRemarks=(String)request.get("remarks");
                    existingJob.setRemarks(existingRemarks.concat(newRemarks));
                }
                if (request.get("apekshaInvoiceNo") != null) {
                    existingJob.setApekshaInvoiceNo((String) request.get("apekshaInvoiceNo"));
                }
                if (request.get("action") != null) {
                    existingJob.setAction((String) request.get("action"));
                }
            }
            else if(roles.contains("ROLE_ADMIN")){
                request.forEach((key,value)->{
                    if (value==null|| key.equals("slNo")) return;
                    switch (key){
                        case "jobId"-> existingJob.setJobId((Integer) value);
                        case "customerName" -> existingJob.setCustomerName((String) value);
                        case "jobDate" -> existingJob.setJobDate(parseDate(value));
                        case "category" -> existingJob.setCategory((String) value);
                        case "sellingPrice" -> existingJob.setSellingPrice((Integer) value);
                        case "jobParticulars" -> existingJob.setJobParticulars((String) value);
                        case "jobReference" -> existingJob.setJobReference((String) value);
                        case "boeSbNo" -> existingJob.setBoeSbNo((String) value);
                        case "boeSbDate" -> existingJob.setBoeSbDate(parseDate(value));
                        case "arrivalDate" -> existingJob.setArrivalDate(parseDate(value));
                        case "tentativeClosureDate" -> existingJob.setTentativeClosureDate(parseDate(value));
                        case "closedDate" -> existingJob.setClosedDate(parseDate(value));
                        case "billingStatus" -> existingJob.setBillingStatus((String) value);
                        case "invoiceNo" -> existingJob.setInvoiceNo((String) value);
                        case "invoiceDate" -> existingJob.setInvoiceDate(parseDate(value));
                        case "courier_tracking_no" -> existingJob.setCourierTrackingNo((String) value);
                        case "payment_status" -> existingJob.setPaymentStatus((String) value);
                        case "remarks" -> existingJob.setRemarks((String) value);
                        case "apekshaInvoiceNo" -> existingJob.setApekshaInvoiceNo((String) value);
                        case "action" -> existingJob.setAction((String) value);
                    }
                });
            }
            existingJob.setUpdatedBy(principal.getName());
            jobRepository.save(existingJob);
            return new ResponseEntity<>("Job Updated", HttpStatus.OK);
        }catch (Exception E){
            return new ResponseEntity<>(E.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
