package com.example.ERP.Models;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;
import lombok.*;

@Data
@Entity(name = "JobStatus")
@Table(name="JobStatus")
@AllArgsConstructor
@NoArgsConstructor
public class JobStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @OneToOne
    @JoinColumn(name="job_slNo",referencedColumnName = "slNo",unique = true)
    private Job job;
    
    @Column(name="crmStatus")
    private String crmStatus;

    @Column(name="billingStatus")
    private String billingStatus;

    @Column(name="operationsStatus")
    private String operationsStatus;
    
    @Column(name="updatedAt")
    private LocalDateTime updatedAt;
    
    @PrePersist
    @PreUpdate
    public void onUpdate(){
        this.updatedAt=LocalDateTime.now();
    }

}
