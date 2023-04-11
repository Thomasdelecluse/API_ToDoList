package fr.apitodolist.apitodolist.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private JwtTokens jwtTokens;
    private final SecretKey secretKey;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokens jwtTokens, SecretKey secretKey){
        this.secretKey = secretKey;
        setAuthenticationManager(authenticationManager);
        setFilterProcessesUrl("/login");
        this.jwtTokens = jwtTokens;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetails user = (UserDetails)authResult.getPrincipal();
        String token = jwtTokens.genereToken(user, secretKey);
        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }
}
