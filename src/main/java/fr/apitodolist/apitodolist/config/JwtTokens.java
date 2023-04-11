package fr.apitodolist.apitodolist.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokens {

    private static final long EXPIRATION_TIME= 1_000_000;

    public String genereToken(UserDetails userDetails, SecretKey secretKey) {
        String login = userDetails.getUsername();
        var roles = userDetails.getAuthorities().stream().map(auth->auth.getAuthority()).collect(Collectors.toList());
        Claims claims = Jwts.claims().setSubject(login);
        claims.put("roles", roles);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    public UsernamePasswordAuthenticationToken decodeToken(String token) {
    }
}
