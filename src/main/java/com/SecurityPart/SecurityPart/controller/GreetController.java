package com.SecurityPart.SecurityPart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class GreetController {
    
    @GetMapping("/greet")
    public ResponseEntity<?> greetUser(){
        return ResponseEntity.ok("Hello User");
    }
}
