package com.example.ERP.Repository;

import com.example.ERP.Models.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job,Long> {
}
