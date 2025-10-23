
package com.example.resumebuilder.service;

import com.example.resumebuilder.model.User;
import com.example.resumebuilder.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    public AuthService(UserRepository userRepository) { this.userRepository = userRepository; }
    public Optional<User> byEmail(String email) { return userRepository.findByEmail(email); }
    public User save(User u) { return userRepository.save(u); }
}
