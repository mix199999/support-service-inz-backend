package com.shopsupport.supportservice.config;

import com.shopsupport.supportservice.applications.CustomUserDetails;
import com.shopsupport.supportservice.entities.User;
import com.shopsupport.supportservice.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtTokenService {

    @Value("${jwt.private-key-file}")
    private String jwtPrivateKey;

    private final UserRepository userRepository;

    @Autowired
    public JwtTokenService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // Metoda do wyciągania nazwy użytkownika z tokena JWT
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Metoda do ładowania danych użytkownika na podstawie nazwy użytkownika
    public UserDetails loadUserByUsername(String jwt) {
        String username = extractUsername(jwt);
        User user = userRepository.findByUsername(username);

        return  new CustomUserDetails(
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }

    // Metoda do walidacji tokena JWT
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtPrivateKey.getBytes()).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Metoda do generowania tokena JWT
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token wygasa po 10 godzinach
                .signWith(SignatureAlgorithm.HS512, jwtPrivateKey.getBytes())
                .compact();
    }
}
