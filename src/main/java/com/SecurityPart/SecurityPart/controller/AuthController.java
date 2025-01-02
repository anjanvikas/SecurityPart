package com.SecurityPart.SecurityPart.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.SecurityPart.SecurityPart.dto.SignupRequest;
import com.SecurityPart.SecurityPart.model.User;
import com.SecurityPart.SecurityPart.service.AuthService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupDto){

        if(authService.userExistsByUsername(signupDto.getUserName())){
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }
        if(authService.userExistsByEmail(signupDto.getEmail())){
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        //new user
        try{
                User user = authService.registerUser(signupDto.getUserName()
                                                    ,signupDto.getEmail()
                                                    ,encoder.encode(signupDto.getPassWord())
                                                    ,signupDto.getRole());
                return ResponseEntity.ok("User registered successfully!");
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Unable to register user. Please try again later." + e);
        }
                    
    }
}
