package fr.apitodolist.apitodolist.service.impl;

import fr.apitodolist.apitodolist.dto.register.UtilisateurDto;
import fr.apitodolist.apitodolist.dto.register.CreateUtilisateurDto;
import fr.apitodolist.apitodolist.modele.Utilisateur;
import fr.apitodolist.apitodolist.repository.IUtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UtilisateurService {
    private static final Logger logger = Logger.getLogger(UtilisateurService.class.getName());
    @Autowired
    IUtilisateurRepository iUtilisateurRepository;
    public UtilisateurDto create(CreateUtilisateurDto createUtilisateurDto) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setLogin(createUtilisateurDto.login());
        utilisateur.setPassword(createUtilisateurDto.password());
        utilisateur.setAdmin(false);
        utilisateur = iUtilisateurRepository.save(utilisateur);
        logger.info("compte crée");
        return new UtilisateurDto(utilisateur.getId(), utilisateur.getLogin(), utilisateur.getPassword(), utilisateur.isAdmin());
    }

}
