package com.example.ERP.DTO;

import lombok.NoArgsConstructor;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobStatusUpdateDTO {

    private String crmStatus;
    private String billingStatus;
    private String operationsStatus;

}
