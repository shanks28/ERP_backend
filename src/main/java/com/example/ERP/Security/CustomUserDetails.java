package com.example.ERP.Security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import com.example.ERP.Models.User;
@Data
@NoArgsConstructor
@AllArgsConstructor
// for Spring Security to understand
public class CustomUserDetails implements UserDetails {
    private String username;
    private String password;
    private String email;
    private Integer otp;
    private Collection<? extends GrantedAuthority> authorities;
    private String last_login;
    public CustomUserDetails(User user) {
        this.username = user.getUserName();
        this.password = user.getPassword();
        this.email=user.getEmail();
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
        this.otp=user.getOTP();
        this.last_login=user.getLastLogin();
    }
}
