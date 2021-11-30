package nl.pcsw.auth.config.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import nl.pcsw.auth.security.domain.PersonDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

@Component
public class JwtTokenProperties {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration.time}")
    private int jwtTokenExpirationTime;

    public String generateToken(Authentication authentication){
        PersonDetails person = (PersonDetails) authentication.getPrincipal();
        return Jwts
                .builder()
                .setClaims(new HashMap<>())
                .setSubject(person.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenExpirationTime * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token, String username) {
        String foundUsername = getUsernameFromToken(token);
        return (Objects.equals(foundUsername, username) && !isTokenExpired(token));
    }

    public String getUsernameFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenExpired(String token) {
        var expirationDate = Jwts
                .parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expirationDate.before(new Date());
    }
}
