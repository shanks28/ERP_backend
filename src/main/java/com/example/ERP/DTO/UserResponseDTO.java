package com.example.ERP.DTO;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.*;
import com.example.ERP.Models.Role;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private String userName;
    private String email;
    private Role role;

    private String lastLogin;
    

}
