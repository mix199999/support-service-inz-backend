package com.shopsupport.supportservice.applications;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

    public CustomUserDetails(int id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super( username, password, authorities);
    }

    // Add a constructor that accepts a list of role strings and converts them to GrantedAuthority objects
    public CustomUserDetails(int id, String username, String password, List<String> roles) {
        super(username, password, rolesToAuthorities(roles));
    }

    // Convert a list of role strings to GrantedAuthority objects
    private static List<GrantedAuthority> rolesToAuthorities(List<String> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Spring Security expects roles to be prefixed with "ROLE_"
                .collect(Collectors.toList());
    }
}
