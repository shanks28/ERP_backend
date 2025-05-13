package com.example.ERP.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonProperty;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithoutJobStatus {

    private Integer slNo;
    private Integer jobId;
    private String customerName;
    private String category;
    private LocalDate jobDate;
    private Integer sellingPrice;
    private Integer costPrice;
    private String remarks;
    private String jobParticulars;
    private String jobReference;
    private String boeSbNo;
    private LocalDate boeSbDate;
    private LocalDate arrivalDate;
    private LocalDate tentativeClosureDate;
    private LocalDate closedDate;
    private String billingStatus;
    private String apekshaInvoiceNo;
    private LocalDate invoiceDate;
    private String courierTrackingNo;
    private String paymentStatus;
    private String action;
    private LocalDate dateOfCourier;
    private LocalDate dutyPaidDate;
    private LocalDate clearanceDate;
    @JsonProperty("isTemp")
    private boolean isTemp;
    private Integer tat;

}
