package com.example.ERP.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WithoutCostPriceDTO {
    private Integer slNo;
    private Integer jobId;
    private LocalDate jobDate;
    private String category;
    private String customerName;
    private String jobParticulars;
    private String jobReference;
    private String boeSbNo;
    private LocalDate boeSbDate;
    private LocalDate arrivalDate;
    private LocalDate tentativeClosureDate;
    private LocalDate closedDate;
    private Integer sellingPrice;
    private String billingStatus;
    private String invoiceNo;
    private LocalDate invoiceDate;
    private String courierTrackingNo;
    private String paymentStatus;
    private String remarks;
    private String apekshaInvoiceNo;
    private LocalDate dateOfCourier;
    private String action;
    private String updatedBy;
    private LocalDateTime updatedAt;
}
