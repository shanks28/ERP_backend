package com.example.ERP.ServiceLayer;

import com.example.ERP.Models.User;
import com.example.ERP.Repository.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.internet.MimeMessage;
import org.apache.coyote.Response;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.HttpStatus;
import com.example.ERP.Models.*;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.util.*;
@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final Dotenv dotenv;
    private final UserRepository userRepository;
    EmailService(JavaMailSender mailSender, Dotenv dotenv, UserRepository userRepository){
        this.mailSender=mailSender;
        this.dotenv=dotenv;
        this.userRepository=userRepository;
    }
    public ResponseEntity<String> sendMail(String dest){
        User obj=userRepository.findByEmail(dest);
        Random random=new Random();
        if (obj==null){
            return new ResponseEntity<>("no such user", HttpStatus.NO_CONTENT);
        }
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(dest);
        message.setFrom("Apeksha@logistics.com");
        message.setSubject("RESET PASSWORD");
        int otp=1000+random.nextInt(9000);
        obj.setOTP(otp);
        userRepository.save(obj);
        message.setText(String.valueOf(otp));
        mailSender.send(message);
        System.out.println(obj);
        return new ResponseEntity<>("email sent",HttpStatus.OK);
    }
    public ResponseEntity<?> sendEmailNotification(JobStatus jobStatus){
        try{
            List<User> adminUsers=userRepository.findByRole(Role.ADMIN);
            if (adminUsers.isEmpty()){
                return new ResponseEntity<>("No admin users found",HttpStatus.NOT_FOUND);
            }
            String body = String.format(
                "Job ID: %s\n" +
                "Serial No: %d\n\n" +
                "Current Status:\n" +
                "CRM: %s\n" +
                "Billing: %s\n" +
                "Operations: %s\n\n" +
                "This job status has been updated in the system.",
                jobStatus.getJob().getJobId(),
                jobStatus.getJob().getSlNo(),
                jobStatus.getCrmStatus(),
                jobStatus.getBillingStatus(),
                jobStatus.getOperationsStatus()
            );
            for(User admins:adminUsers){
                SimpleMailMessage message=new SimpleMailMessage();
                message.setTo(admins.getEmail());
                message.setSubject("Job Status Update"+jobStatus.getJob().getJobId());
                message.setText(body);
                mailSender.send(message);
            }
            return new ResponseEntity<>("Email notification sent successfully",HttpStatus.OK);
            
        }catch (Exception e){
            return new ResponseEntity<>("Failed to send email notification",HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }
}
