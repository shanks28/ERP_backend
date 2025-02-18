package com.example.ERP.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//can be considered as middleware ig
public class SecurityConfig {
    @Bean //IOC purpose
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean//IOC purpose
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**","/login").permitAll() // Allow all under /auth without authenticating
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/crm/**").hasAnyRole("ADMIN","CRM")
                        .requestMatchers("/billing/**").hasAnyRole("ADMIN","BILLING")
                        .anyRequest().authenticated() //protect everything else
                )
                .formLogin().disable() // Disable default form login
                .logout()
                    .logoutUrl("/auth/logout")
                    .logoutSuccessUrl("/auth")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID");
        return http.build();
    }

}
