package fr.apitodolist.apitodolist.config;

import fr.apitodolist.apitodolist.config.error.BadTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.Key;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtTokens jwtTokens;
    private SecretKey secretKey;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtTokens jwtTokens, SecretKey secretKey) {
        super(authenticationManager);
        this.jwtTokens = jwtTokens;
        this.secretKey = secretKey;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader (HttpHeaders.AUTHORIZATION);
        if (token == null || token.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = null;
        try {
            authentication = jwtTokens.decodeToken(token, secretKey);
            SecurityContextHolder.getContext().setAuthentication (authentication);
        } catch (BadTokenException e) {
            SecurityContextHolder.clearContext();
        }
        chain.doFilter(request, response);
    }
}
