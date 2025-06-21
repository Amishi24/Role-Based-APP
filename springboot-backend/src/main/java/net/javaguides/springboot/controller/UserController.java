package net.javaguides.springboot.controller;

import net.javaguides.springboot.dto.UserLoginDto;
import net.javaguides.springboot.dto.UserRegistrationDto;
import net.javaguides.springboot.service.UserService;
import net.javaguides.springboot.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200") 
public class UserController {
    
	@Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationDto dto) {
    	try {
            userService.registerUser(dto);
            return ResponseEntity.ok("User registered successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto dto) {
        try {
            LoginResponseDto result = userService.loginUser(dto);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if ("User does not exist".equals(msg)) {
                return ResponseEntity.status(404).body(msg);
            } else if ("Incorrect password".equals(msg)) {
                return ResponseEntity.status(401).body(msg);
            } else if ("Not approved yet".equals(msg)) {
                return ResponseEntity.status(403).body(msg);
            }
            return ResponseEntity.status(400).body(msg);
        }
    }
    
}
