package fr.apitodolist.apitodolist.config;

import fr.apitodolist.apitodolist.modele.Utilisateur;
import fr.apitodolist.apitodolist.repository.IUtilisateurRepository;
import fr.apitodolist.apitodolist.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Configuration
public class BaseInitializer {
    private static final Logger logger = Logger.getLogger(CustomUserDetailsService.class.getName());
    @Autowired
    IUtilisateurRepository iUtilisateurRepository;

    @Bean
    public ApplicationRunner initializerUser(){
        return (args) -> {
            Utilisateur user = new Utilisateur("user","user",false);
            iUtilisateurRepository.save(user);
            Utilisateur admin = new Utilisateur("admin","admin",true);
            iUtilisateurRepository.save(admin);
            logger.info("utilisateur crée");
        };
    }
}
