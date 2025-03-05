package com.example.ERP.ServiceLayer;

import com.example.ERP.Models.Job;
import com.example.ERP.Repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CRMService {
    private final JobRepository jobRepository;
    CRMService(JobRepository jobRepository){
        this.jobRepository=jobRepository;
    }
    public List<Job> getAllRecords(){
        return jobRepository.findAll();
    }
}
