package com.example.ERP.DTO;

import com.example.ERP.Controller.Admin;
import com.example.ERP.Models.Job;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class JobDTo {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CRMEntryRequest{

        private String customerName;
        
        private boolean isTemp;

        @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
        private LocalDate date;

        private String category;
        
        private Integer sellingPrice;
    }

}
