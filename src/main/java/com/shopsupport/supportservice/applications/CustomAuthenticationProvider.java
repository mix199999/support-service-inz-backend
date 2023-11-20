package com.shopsupport.supportservice.applications;


import com.shopsupport.supportservice.entities.User;
import com.shopsupport.supportservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository; // You need to create this repository to interact with your database

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Retrieve the user from your database based on the username
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Implement your password validation logic here (e.g., compare hashed passwords)
        if (!isPasswordValid(user, password)) {
            throw new BadCredentialsException("Invalid username or password");
        }

        // Create a UserDetails object based on your custom User entity
        UserDetails userDetails = new CustomUserDetails(
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities() // Assuming user.getRoles() returns a list of role strings
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        // If authentication is successful, create a new Authentication object with the user's authorities
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public boolean isPasswordValid(User user, String rawPassword) {

        String hashedPassword = user.getPassword(); // Retrieve the hashed password from the user entity
        return BCrypt.checkpw(rawPassword, hashedPassword); // Use a password hashing library (e.g., BCrypt) for secure password comparison
    }


}
