package com.example.ERP.Repository;

import com.example.ERP.Models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {
    Job findByJobId(Integer jobId);
    @Query("SELECT COALESCE(MAX(J.jobId),0) From Job J")
    Integer findLatestJobId();
}
