package fr.apitodolist.apitodolist.config;

import fr.apitodolist.apitodolist.config.error.BadTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokens {

    private static final long EXPIRATION_TIME= 1_000_000;
    public static final String PREFIX = "Bearer ";

    public String genereToken(UserDetails userDetails, SecretKey secretKey) {
        String login = userDetails.getUsername();
        var roles = userDetails.getAuthorities().stream().map(auth->auth.getAuthority()).collect(Collectors.toList());
        Claims claims = Jwts.claims().setSubject(login);
        claims.put("roles", roles);
        claims.setIssuedAt(new Date());
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    public UsernamePasswordAuthenticationToken decodeToken(String token, SecretKey secretKey) throws BadTokenException {
        //enlever entete token Bearer
        if (token.startsWith(PREFIX)) {
            token = token.replaceFirst(PREFIX, "");
        }
        try {
            Jws<Claims> jwsClaims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            //Ok le token est bon
            String login = jwsClaims.getBody().getSubject();

            List<String> roles = jwsClaims.getBody().get("roles", List.class);

            List<SimpleGrantedAuthority> authorities =
                    roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
            return new UsernamePasswordAuthenticationToken(login, null, authorities);
        } catch (JwtException e) {
            throw new BadTokenException();
        }
    }
}
