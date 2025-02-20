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
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest{
        private String username;
        private String password;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResetPassword{
        private String email;
    }
}
