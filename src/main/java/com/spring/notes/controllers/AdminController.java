package com.spring.notes.controllers;

import com.spring.notes.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        try{
            return userService.getAllUsers();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No Users!"+e);
        }
    }

    @PostMapping("/changeRole")
    public ResponseEntity<String> changeUserRole(@RequestParam Long userId,
                               @RequestParam String changedRole){

        try{
            userService.changeUserRole(userId, changedRole);
            return ResponseEntity.ok("User role changed!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Couldn't update role of user"+e);
        }

    }

}
