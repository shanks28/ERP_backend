package com.example.ERP.ServiceLayer;

import com.example.ERP.DTO.AuthDTO;
import com.example.ERP.DTO.AuthDTO.completeRegisterRequest;
import com.example.ERP.DTO.AuthDTO.registerRequest;
import com.example.ERP.Models.User;
import com.example.ERP.Repository.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.Response;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.javamail.JavaMailSender;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import java.time.Duration; // Add this import

@Service
public class Auth {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JavaMailSender javaMailSender;

    Auth(JavaMailSender javaMailSender,UserRepository userRepository,PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager) {
        this.authenticationManager=authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
        this.javaMailSender=javaMailSender;

    }
    @Transactional
    public ResponseEntity<String> completeRegister(completeRegisterRequest request){
        try{
            User user=userRepository.findByEmail(request.getEmail());
            if(user==null){
                return new ResponseEntity<>("User does not exist",HttpStatus.BAD_REQUEST);
            }
            user.setPassword(passwordEncoder.encode(request.getPassword()));// encode while setting
            user.setActive(true);
            userRepository.save(user);
            return new ResponseEntity<>("User Registered",HttpStatus.OK);

        }catch(Exception e){
            return new ResponseEntity<>(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }
    @Transactional
    public ResponseEntity<String> register(registerRequest details) {
        try{
            User existingUser = userRepository.findByUserName(details.getUserName());
            if (existingUser != null) {
                return new ResponseEntity<>("User Already Exists",HttpStatus.BAD_REQUEST); // 4XX
            }
            User newUser=new User();
            newUser.setUserName(details.getUserName());
            newUser.setPassword("");
            newUser.setEmail(details.getEmail());
            newUser.setRole(details.getRole());
            userRepository.save(newUser);
            String verificationURL="http://15.206.32.228:5174/set-password?email=" + 
                            details.getEmail();
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(details.getEmail());
            mailMessage.setSubject("Complete Registration");
            mailMessage.setText("Click the link to complete your registration: " + verificationURL);
            javaMailSender.send(mailMessage);

            return new ResponseEntity<>("Registeration Mail Sent",HttpStatus.OK); //2XX
        }
        catch(Exception e){
            return new ResponseEntity<>(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<?> login(AuthDTO.LoginRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            User user = userRepository.findByUserName(request.getUsername());
            if (user == null || !user.isActive()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not active or does not exist");
            }

            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            user.setLastLogin(localDateTime.format(dateTimeFormatter));
            userRepository.save(user);

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);

            HttpSession session = httpRequest.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

            // Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());
            // sessionCookie.setHttpOnly(true);
            // sessionCookie.setSecure(httpRequest.isSecure()); // Always true for HTTPS
            // sessionCookie.setPath("/");
            // httpResponse.addCookie(sessionCookie);
            httpResponse.setHeader("Set-Cookie",
            String.format("JSESSIONID=%s; Path=/; HttpOnly; Max-Age=86400", session.getId()));
            String origin = httpRequest.getHeader("Origin");
            if (origin != null) {
                httpResponse.setHeader("Access-Control-Allow-Origin", origin);
            }
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Expose-Headers", "Set-Cookie");
            AuthDTO.LoginResponse responseDto = new AuthDTO.LoginResponse(user.getEmail(), user.getRole());
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username/password incorrect or other login error");
        }
    }


    public ResponseEntity<String> verifyOtp(String email, String otp){
        User obj=userRepository.findByEmail(email);
        if (obj.getOTP().equals(Integer.parseInt(otp))){
            return new ResponseEntity<>("Verified",HttpStatus.OK);
        }
        return new ResponseEntity<>("Incorrect OTP",HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<String> resetPassword(String email,String new_password){
        User obj=userRepository.findByEmail(email);
        obj.setPassword(passwordEncoder.encode(new_password));
        obj.setOTP(null);
        userRepository.save(obj);
        return new ResponseEntity<>("password reset",HttpStatus.OK);
    }


}
