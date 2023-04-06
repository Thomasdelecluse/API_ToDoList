package fr.apitodolist.apitodolist;

import fr.apitodolist.apitodolist.modele.Utilisateur;
import fr.apitodolist.apitodolist.repository.IUtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class BaseInitializer {

    @Autowired
    IUtilisateurRepository iUtilisateurRepository;

    @Bean
    public ApplicationRunner initializerUser(){
        return (args) -> {
            Utilisateur user = new Utilisateur("user","user",false);
            iUtilisateurRepository.save(user);
            Utilisateur admin = new Utilisateur("admin","admin",true);
            iUtilisateurRepository.save(admin);
            System.out.println("utilisateur crée");
        };
    }
}
