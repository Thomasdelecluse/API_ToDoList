package fr.apitodolist.apitodolist.controller;

import fr.apitodolist.apitodolist.dto.register.UtilisateurDto;
import fr.apitodolist.apitodolist.dto.register.CreateUtilisateurDto;
import fr.apitodolist.apitodolist.service.impl.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@CrossOrigin
public class UtilisateurController {

    @Autowired
    UtilisateurService utilisateurService;


    @PostMapping("/utilisateurs")
    public ResponseEntity<?> create(@RequestBody CreateUtilisateurDto createUtilisateurDto) {
        try {
            UtilisateurDto createAccount = utilisateurService.create(createUtilisateurDto);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createAccount.login())
                    .toUri();
            return ResponseEntity.created(location).body(createAccount);
        } catch (Exception e) {
            String errorMessage = "Une erreur est survenue lors de la création de l'utilisateur : " + e.getMessage();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        }
    }
}
