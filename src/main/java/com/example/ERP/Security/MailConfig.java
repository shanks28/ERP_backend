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
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Safely retrieve environment variables from .env
        String host = dotenv.get("MAIL_HOST");
        String port = dotenv.get("MAIL_PORT");
        String username = dotenv.get("MAIL_USERNAME");
        String password = dotenv.get("MAIL_PASSWORD");

        if (host == null || port == null || username == null || password == null) {
            throw new IllegalStateException("Missing one or more mail configuration environment variables in .env");
        }

        mailSender.setHost(host);
        mailSender.setPort(Integer.parseInt(port));
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        System.out.println("MAIL_PASSWORD (Debug Only): " + password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }
}
