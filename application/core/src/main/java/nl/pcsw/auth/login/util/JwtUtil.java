package nl.pcsw.auth.login.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import nl.pcsw.auth.security.domain.PersonDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

public class JwtUtil {

    private final String jwtSecret;
    private final int jwtTokenExpirationTime;

    public JwtUtil(String jwtSecret, int jwtTokenExpirationTime) {
        this.jwtSecret = jwtSecret;
        this.jwtTokenExpirationTime = jwtTokenExpirationTime;
    }

    public String generateToken(Authentication authentication){
        PersonDetails person = (PersonDetails) authentication.getPrincipal();
        return Jwts
                .builder()
                .setClaims(new HashMap<>())
                .setSubject(person.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenExpirationTime * 1000L))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
