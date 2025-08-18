package com.spring.notes.services;

import com.spring.notes.entities.User;
import com.spring.notes.repositories.UserRepository;
import com.spring.notes.security.models.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(String email,String username, String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);

        userRepository.save(user);
    }

    public UserDetails loginUser(String username, String rawPassword){
        var user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Username not found"));
        if(passwordEncoder.matches(rawPassword,user.getPassword())){
            return new CustomUserDetails(user);
        }
        else {
            throw new IllegalArgumentException("Wrong Password");
        }
    }

}
