package fr.apitodolist.apitodolist.service.impl;

import fr.apitodolist.apitodolist.dto.register.CreateUtilisateurDto;
import fr.apitodolist.apitodolist.dto.register.UtilisateurDto;
import fr.apitodolist.apitodolist.modele.Utilisateur;
import fr.apitodolist.apitodolist.repository.IUtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.logging.Logger;

@Service
public class UtilisateurService {
    private static final Logger logger = Logger.getLogger(UtilisateurService.class.getName());
    @Autowired
    IUtilisateurRepository iUtilisateurRepository;
    public UtilisateurDto create(CreateUtilisateurDto createUtilisateurDto) {
        Utilisateur usersAlreadyExist = iUtilisateurRepository.findByLogin(createUtilisateurDto.login());
        //check if login already exist
        if(usersAlreadyExist != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login already exist");
        }
        //create new utilisateur
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setLogin(createUtilisateurDto.login());
        utilisateur.setPassword(createUtilisateurDto.password());
        utilisateur.setAdmin(false);
        //save utilisateur
        utilisateur = iUtilisateurRepository.save(utilisateur);
        logger.info("Le compte " + utilisateur.getLogin() + " a été créé");
        return new UtilisateurDto(utilisateur.getId(), utilisateur.getLogin(), utilisateur.getPassword(), utilisateur.isAdmin());
    }

    public UtilisateurDto fetchByLogin(String login) {
       Utilisateur utilisateur = iUtilisateurRepository.findByLogin(login);
        return new UtilisateurDto(utilisateur.getId(), utilisateur.getLogin(), utilisateur.getPassword(), utilisateur.isAdmin());
    }

    public ArrayList<UtilisateurDto> fetchAll() {
        Iterable<Utilisateur> utilisateurs = iUtilisateurRepository.findAll();
        ArrayList<UtilisateurDto>  listOfUtilisateur = new ArrayList<>();

        for(Utilisateur utilisateur : utilisateurs){
            UtilisateurDto utilisateurDto = new UtilisateurDto(utilisateur.getId(), utilisateur.getLogin(), utilisateur.getPassword(), utilisateur.isAdmin());
            listOfUtilisateur.add(utilisateurDto);
        }
        if(listOfUtilisateur.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aucun utilisateurs en base");
        }
        return listOfUtilisateur;
    }

}
