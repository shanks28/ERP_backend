package com.example.ERP.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Job")
@ToString
public class Job {
    public Job(Integer jobId, String customerName, String category, LocalDate localDate,Integer sellingPrice){
        this.jobId=jobId;
        this.customerName=customerName;
        this.jobDate=localDate;
        this.category=category;
        this.sellingPrice=sellingPrice;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer slNo;

    @Column(name = "job_id",nullable = false)
    private Integer jobId;
    @Column(name="jobDate",nullable = false)
    private LocalDate jobDate;
    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "job_particulars")
    private String jobParticulars;

    @Column(name = "job_reference")
    private String jobReference;

    @Column(name = "boe_sb_no")
    private String boeSbNo;

    @Column(name = "boe_sb_date")
    private LocalDate boeSbDate;

    @Column(name = "arrival_date")
    private LocalDate arrivalDate;

    @Column(name = "tentative_closure_date")
    private LocalDate tentativeClosureDate;

    @Column(name = "closed_date")
    private LocalDate closedDate;

    @Column(name="selling_price")
    private Integer sellingPrice;

    @Column(name="cost_price")
    private Integer costPrice;

    @Column(name = "billing_status")
    private String billingStatus;

    @Column(name = "invoice_no")
    private String invoiceNo;

    @Column(name = "invoice_date")
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private LocalDate invoiceDate;

    @Column(name = "courier_tracking_no")
    private String courierTrackingNo;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "apeksha_invoice_no")
    private String apekshaInvoiceNo;

    @Column(name = "date_of_courier")
    private LocalDate dateOfCourier;

    @Column(name = "Action")
    private String action;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Version
    private Long version;

    @PreUpdate
    @PrePersist
    public void onUpdate(){
        this.updatedAt=LocalDateTime.now();
    }
}
