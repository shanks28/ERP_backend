package com.example.ERP.ServiceLayer;

import com.example.ERP.DTO.AuthDTO;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class Auth {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    Auth(UserRepository userRepository,PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager) {
        this.authenticationManager=authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;

    }

    public ResponseEntity<String> register(User details) {
        User existingUser = userRepository.findByUserName(details.getUserName());
        if (existingUser != null) {
            return new ResponseEntity<>("User Already Exists",HttpStatus.BAD_REQUEST); // 4XX
        }
        details.setPassword(passwordEncoder.encode(details.getPassword()));
        userRepository.save(details);
        return new ResponseEntity<>("User Registered",HttpStatus.OK); //2XX

    }
    public ResponseEntity<?> login(AuthDTO.LoginRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // Retrieve user and update last login time
            User user = userRepository.findByUserName(request.getUsername());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            user.setLastLogin(localDateTime.format(dateTimeFormatter));
            userRepository.save(user);

            // Set the security context
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);

            // Save security context in session using HttpSessionSecurityContextRepository
            SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
            securityContextRepository.saveContext(securityContext, httpRequest, httpResponse);

            // Create or retrieve session and attach security context
            HttpSession session = httpRequest.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

            // Create session cookie with proper settings
            Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());
            sessionCookie.setHttpOnly(true); // enhance security by preventing client-side access
            sessionCookie.setSecure(httpRequest.isSecure()); // mark secure if connection is secure
            sessionCookie.setPath("/");
            // Note: Setting SameSite may require additional handling depending on your container
            // sessionCookie.setAttribute("SameSite", "None");
            httpResponse.addCookie(sessionCookie);

            // Construct and return the custom response
            AuthDTO.LoginResponse response = new AuthDTO.LoginResponse(user.getEmail(), user.getRole());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username/password incorrect");
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
