package fr.apitodolist.apitodolist.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtTokens jwtTokens;
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtTokens jwtTokens) {
        super(authenticationManager);
        this.jwtTokens = jwtTokens;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader (HttpHeaders.AUTHORIZATION);
        UsernamePasswordAuthenticationToken authentication = jwtTokens.decodeToken(token);
        SecurityContextHolder.getContext().setAuthentication (authentication);
        chain.doFilter(request, response);
    }
}
