package com.spring.notes.services;

import com.spring.notes.entities.Role;
import com.spring.notes.entities.User;
import com.spring.notes.repositories.UserRepository;
import com.spring.notes.security.models.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userRepository.findAll();
        if(!users.isEmpty()){
            return ResponseEntity.ok(users);
        }else
            throw new IllegalArgumentException("No users found");
    }

    public void changeUserRole(Long userId, String changedRole){
        var user = userRepository.findById(userId)
                .orElseThrow();

        try{
            user.setRole(Role.valueOf(changedRole));
            userRepository.save(user);
        }catch (Exception e){
            throw new IllegalArgumentException("Error changing role! " +e);
        }

    }

    public User findByUsername(String username){
       return userRepository.findByUsername(username)
               .orElseThrow(()-> new IllegalArgumentException("user not found"));
    }

}
