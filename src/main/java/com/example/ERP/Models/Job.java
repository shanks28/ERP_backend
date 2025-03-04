package com.example.ERP.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long slNo;

    @Column(name = "jobId")
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

    @Column(name = "billing_status")
    private String billingStatus;

    @Column(name = "invoice_no")
    private String invoiceNo;

    @Column(name = "invoice_date")
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
    private Integer action;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
