package fr.apitodolist.apitodolist.controller;

import fr.apitodolist.apitodolist.dto.register.UtilisateurDto;
import fr.apitodolist.apitodolist.dto.register.CreateUtilisateurDto;
import fr.apitodolist.apitodolist.service.impl.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@CrossOrigin
public class UtilisateurController {

    @Autowired
    UtilisateurService utilisateurService;


    @PostMapping("/utilisateur")
    public ResponseEntity<UtilisateurDto> create(@RequestBody CreateUtilisateurDto createUtilisateurDto) {
        UtilisateurDto createAccount = utilisateurService.create(createUtilisateurDto);
        return ResponseEntity.created(URI.create("/todos/" + createAccount.id())).body(createAccount);
    }
}
