
package com.example.resumebuilder.controller;

import com.example.resumebuilder.model.User;
import com.example.resumebuilder.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController @RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    public AuthController(UserRepository userRepository){ this.userRepository=userRepository; }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        Optional<User> existing = userRepository.findByEmail(user.getEmail());
        if(existing.isPresent()) return ResponseEntity.badRequest().body("Email already exists");
        return ResponseEntity.ok(userRepository.save(user));
    }
}
