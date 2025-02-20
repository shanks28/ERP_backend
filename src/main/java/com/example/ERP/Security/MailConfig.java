package com.example.ERP.Security;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    private final Dotenv dotenv;
    public MailConfig(Dotenv dotenv){
        this.dotenv=dotenv;
    }
    @Bean
    public JavaMailSender javaMailSender() throws Exception{
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        String host = dotenv.get("MAIL_HOST");
        String port = dotenv.get("MAIL_PORT");
        String username = dotenv.get("MAIL_USERNAME");
        String password = dotenv.get("MAIL_PASSWORD");
        mailSender.setHost(host);
        mailSender.setPort(Integer.parseInt(port));
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }
}
