package com.example.ERP.Repository;
import com.example.ERP.Models.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface jobStatusRepository extends JpaRepository<JobStatus, Integer> {
    
    JobStatus findByJob_SlNo(Integer slNo);
    JobStatus findByJobSlNo(Integer slNo);
}
