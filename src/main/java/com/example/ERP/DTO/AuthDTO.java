package com.example.ERP.DTO;
import com.example.ERP.Models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AuthDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthResponse {
        private String username;
        private Role role;
    }
}
