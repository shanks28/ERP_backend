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
@ToString
public class JobStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @OneToOne
    @JoinColumn(name="job_sl_no",referencedColumnName = "slNo",unique = true)
    private Job job;
    
    @Column(name="crmStatus")
    private String crmStatus;

    @Column(name="billingStatus")
    private String billingStatus;
// CRM->OPERATION->BILLIING
    @Column(name="operationsStatus")
    private String operationsStatus;
    // User goes to the category field to enter details at the same time it should make an API call to the cat table and 
    //send all availaible cats in addition to whatever is there .
    // 1) ADD (POST)
    // 2)GET (ALL_AVAILAIBLE)
    //  3) PATCH(UPDATE/DELETE) 
    @Column(name="updatedAt")
    private LocalDateTime updatedAt;
    
    @PrePersist
    @PreUpdate
    public void onUpdate(){
        this.updatedAt=LocalDateTime.now().withNano(0);
    }

}
