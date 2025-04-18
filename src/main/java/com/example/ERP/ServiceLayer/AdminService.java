package com.example.ERP.ServiceLayer;

import com.example.ERP.DTO.UserResponseDTO;
import com.example.ERP.Models.User;
import com.example.ERP.Repository.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final Dotenv dotenv;

    public AdminService(UserRepository userRepository,Dotenv dotenv){
        this.userRepository=userRepository;
        this.dotenv=dotenv;
    }

    public ResponseEntity<?> getAllUsers(){
        List<User> allUsers=userRepository.findAll();
        List<UserResponseDTO> response= allUsers.stream().map(user->{UserResponseDTO dto=new UserResponseDTO();
                    BeanUtils.copyProperties(user, dto);
                return dto;}
                ).toList();
        return new ResponseEntity<>(response,HttpStatus.OK);         
    }
}
