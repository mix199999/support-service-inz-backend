package com.shopsupport.supportservice.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopsupport.supportservice.applications.CustomAuthenticationProvider;
import com.shopsupport.supportservice.entities.User;
import com.shopsupport.supportservice.repositories.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

//    @GetMapping("/")
//    String test()
//    {
//        return "Page /";
//    }
//
//    @GetMapping("/users")
//    List<User> getAll()
//    {
//        return userRepository.findAll();
//    }
//
//    @GetMapping("/user/dashboard")
//
//    public String userDashboard() {
//        // Handle user dashboard
//        return "user_dashboard";
//    }
//
//    @GetMapping("/admin/dashboard")
//
//    public String adminDashboard() {
//        // Handle admin dashboard
//        return "admin_dashboard";
//    }
//
//
    @PostMapping("/loginForm")
   @CrossOrigin("http://localhost:3000")
    public ResponseEntity<String> login(@RequestBody User user) {
        // Pobierz dane użytkownika z bazy danych na podstawie jego nazwy użytkownika
        User userFromDatabase = userRepository.findByUsername(user.getUsername());
        CustomAuthenticationProvider customAuthenticationProvider = new CustomAuthenticationProvider();

        if (userFromDatabase == null) {
            // Użytkownik o podanej nazwie nie istnieje
            return new ResponseEntity<>("Użytkownik o podanej nazwie nie istnieje", HttpStatus.UNAUTHORIZED);
        }

        // Sprawdź, czy hasło podane przez użytkownika jest poprawne
        if (!customAuthenticationProvider.isPasswordValid(userFromDatabase, user.getPassword())) {
            // Hasło jest niepoprawne
            return new ResponseEntity<>("Niepoprawne hasło", HttpStatus.UNAUTHORIZED);
        }

        // Przekształć dane użytkownika na format JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String userJson = objectMapper.writeValueAsString(userFromDatabase);
            return new ResponseEntity<>(userJson, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Błąd przetwarzania danych", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/messages")
    public ResponseEntity<List<String>> messages(){
        return ResponseEntity.ok(Arrays.asList("first", "second"));
    }


    @PostMapping("/test-request")
    public ResponseEntity<String> testPostRequest() {
        return ResponseEntity.ok("POST request successful");
    }



}
