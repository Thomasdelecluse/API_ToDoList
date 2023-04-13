package fr.apitodolist.apitodolist.service.impl;

import fr.apitodolist.apitodolist.modele.Utilisateur;
import fr.apitodolist.apitodolist.repository.IUtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.logging.Logger;

public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = Logger.getLogger(CustomUserDetailsService.class.getName());
    private static final String[] ROLES_ADMIN = {"USER", "ADMIN"};
    private static final String[] ROLES_USER = {"USER"};
    @Autowired
    IUtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByLogin(username);
        if (utilisateur == null) {
            // Aucun utilisateur avec ce nom d'utilisateur n'a été trouvé
            throw new UsernameNotFoundException("Nom d'utilisateur introuvable : " + username);

        }
        // Le nom d'utilisateur correspond, retourner l'utilisateur trouvé
        String[] roles = utilisateur.isAdmin() ? ROLES_ADMIN : ROLES_USER;
        UserDetails userDetails = User.builder()
                .username(utilisateur.getLogin())
                .password(passwordEncoder.encode(utilisateur.getPassword()))
                .roles(roles)
                .build();
        logger.info("Utilisateur connecté" + " " + username);
        return userDetails;
    }

}
