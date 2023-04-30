package fr.apitodolist.apitodolist.config;

import fr.apitodolist.apitodolist.service.impl.CustomUserDetailsService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private JwtTokens jwtTokens;

    //desactive la securité csrf et le cors pour accepter les requetes de n'importe quel domaine
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        return http
                .cors().and().csrf().disable()
                        .addFilter(new JwtAuthenticationFilter(authenticationManagerBuilder.getObject(), jwtTokens, secretKey))
                        .addFilter(new JwtAuthorizationFilter(authenticationManagerBuilder.getObject(), jwtTokens, secretKey))
                .authorizeHttpRequests()
                        .requestMatchers(HttpMethod.POST, "/utilisateurs")
                            .permitAll()
                        .requestMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**")
                            .permitAll()
                        .requestMatchers("/h2-console/**")
                            .permitAll()
                        .requestMatchers(HttpMethod.GET,"/test")
                            .hasRole("ADMIN")
                        .anyRequest()
                            .hasRole("USER")
                        .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and()
                            .headers().frameOptions().disable()
                        .and()
                            .build();
    }

    // Configuration de la gestion des CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
