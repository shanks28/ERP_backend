package com.example.ERP.ServiceLayer;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final Dotenv dotenv;
    EmailService(JavaMailSender mailSender,Dotenv dotenv){
        this.mailSender=mailSender;
        this.dotenv=dotenv;
    }
    public void sendMail(String dest,String otp){
        try{
            System.out.println(dotenv.get("MAIL_PASSWORD"));
            SimpleMailMessage message=new SimpleMailMessage();
            message.setTo(dest);
            message.setFrom("gaunterodim68@gmail.com");
            message.setSubject("RESET PASSWORD");
            message.setText(otp);
            mailSender.send(message);
            System.out.println("email sent");
        }catch (Exception E){
            E.getLocalizedMessage();
        }

    }
}
