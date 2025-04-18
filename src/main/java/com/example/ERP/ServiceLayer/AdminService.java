package com.example.ERP.ServiceLayer;

import com.example.ERP.DTO.UserResponseDTO;
import com.example.ERP.Models.User;
import com.example.ERP.Repository.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.transaction.Transactional;
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
    @Transactional
    public ResponseEntity<?> deleteUser(String userName){
        try{
            User user=userRepository.findByUserName(userName);
            if (user==null){
                return new ResponseEntity<>("User Not Found",HttpStatus.BAD_REQUEST);
            }
            userRepository.delete(user);
            return new ResponseEntity<>("User Deleted",HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("User Not Found",HttpStatus.BAD_REQUEST);}
    }
}
