package fr.apitodolist.apitodolist.config.constant;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager){
        setAuthenticationManager(authenticationManager);
        setFilterProcessesUrl("/login");
    }

    @Override
    protected void successfulAuthentication(){

    }

}
