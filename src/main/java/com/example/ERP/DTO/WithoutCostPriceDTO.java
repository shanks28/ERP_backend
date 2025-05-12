package com.example.ERP.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WithoutCostPriceDTO {
    private Integer slNo;
    private Integer jobId;
    private boolean isTemp;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate jobDate;
    private String category;
    private String customerName;
    private String jobParticulars;
    private String jobReference;
    private String boeSbNo;
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private LocalDate boeSbDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate arrivalDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate tentativeClosureDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate closedDate;
    private String billingStatus;
    // private String invoiceNo;
    private LocalDate invoiceDate;
    private String courierTrackingNo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dutyPaidDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate clearanceDate;
    private String paymentStatus;
    private String remarks;
    private String apekshaInvoiceNo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateOfCourier;
    private String action;
    private String updatedBy;
    private LocalDateTime updatedAt;
    private Integer tat;
}
