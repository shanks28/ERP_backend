package com.example.ERP.ServiceLayer;

import com.example.ERP.Models.User;
import com.example.ERP.Repository.UserRepository;
import com.example.ERP.Security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    CustomUserDetailsService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username){
        User user=userRepository.findByUserName(username);
        if(user==null){
            throw new UsernameNotFoundException("Username Not found");
        }
        return new CustomUserDetails(user);
    }
}
