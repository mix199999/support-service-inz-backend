package com.shopsupport.supportservice.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopsupport.supportservice.applications.CustomAuthenticationProvider;
import com.shopsupport.supportservice.applications.CustomUserDetails;
import com.shopsupport.supportservice.config.JwtTokenService;
import com.shopsupport.supportservice.dto.UserDto;
import com.shopsupport.supportservice.entities.User;
import com.shopsupport.supportservice.repositories.*;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static javax.crypto.Cipher.SECRET_KEY;
@RequiredArgsConstructor
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtTokenService jwtTokenService; // Dodaj to pole

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        // Handle admin dashboard
        return "admin_dashboard";
    }



    @PostMapping("/test-request")
    public ResponseEntity<String> testPostRequest() {
        return ResponseEntity.ok("POST request successful");
    }


    @GetMapping("/userInfo")
    public ResponseEntity<String> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            // Access user details such as username, authorities, etc.
            return new ResponseEntity<>("User Info: " + userDetails.getUsername(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        // Pobierz dane użytkownika z bazy danych na podstawie jego nazwy użytkownika
        User userFromDatabase = userRepository.findByUsername(user.getUsername());


        if (userFromDatabase == null) {
            // Użytkownik o podanej nazwie nie istnieje
            return new ResponseEntity<>("Użytkownik o podanej nazwie nie istnieje", HttpStatus.UNAUTHORIZED);
        }

        // Sprawdź, czy hasło podane przez użytkownika jest poprawne
        if (!customAuthenticationProvider.isPasswordValid(userFromDatabase, user.getPassword())) {
            // Hasło jest niepoprawne
            return new ResponseEntity<>("Niepoprawne hasło", HttpStatus.UNAUTHORIZED);
        }

        // Utwórz UserDetails
        UserDetails userDetails = new CustomUserDetails(
                userFromDatabase.getUserId(),
                userFromDatabase.getUsername(),
                userFromDatabase.getPassword(),
                userFromDatabase.getAuthorities()
        );

        // Wygeneruj token JWT
        String token = jwtTokenService.generateToken(userDetails);

        UserDto userDto = new UserDto();
        userDto.setLogin(userFromDatabase.getUsername());
        userDto.setROLE(userFromDatabase.getRole());
        userDto.setId(userFromDatabase.getUserId());
        userDto.setToken(token);


        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = null;
        try {
            userJson = objectMapper.writeValueAsString(userDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(userJson, HttpStatus.OK);




    }






}
