package fr.apitodolist.apitodolist.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import fr.apitodolist.apitodolist.dto.login.SuccessFullLoginDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.PrintWriter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private JwtTokens jwtTokens;
    private final SecretKey secretKey;
    private ObjectMapper objectMapper = new ObjectMapper(); // Pour désérialiser les données JSON du corps de la requête
    private ObjectWriter objectWriter = objectMapper.writer();
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokens jwtTokens, SecretKey secretKey){
        this.secretKey = secretKey;
        setAuthenticationManager(authenticationManager);
        setFilterProcessesUrl("/login");
        this.jwtTokens = jwtTokens;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // Récupération des données d'identification depuis le corps de la requête HTTP
            JsonNode jsonNode = objectMapper.readTree(request.getInputStream());
            String username = jsonNode.get("username").asText();
            String password = jsonNode.get("password").asText();

            // Création de l'objet d'authentification
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    username, password);

            // Authentification de l'utilisateur
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetails user = (UserDetails)authResult.getPrincipal();
        String token = jwtTokens.genereToken(user, secretKey);
        var successfulLoginDto = new SuccessFullLoginDto(token);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(objectWriter.writeValueAsString(successfulLoginDto));
        out.flush();
    }
}

