package fr.apitodolist.apitodolist.controller;

import fr.apitodolist.apitodolist.config.error.FunctionalException;
import fr.apitodolist.apitodolist.dto.register.CreateUtilisateurDto;
import fr.apitodolist.apitodolist.dto.register.UtilisateurDto;
import fr.apitodolist.apitodolist.service.impl.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;

@RestController
@CrossOrigin
public class UtilisateurController {

    @Autowired
    UtilisateurService utilisateurService;


    @PostMapping("/utilisateurs")
    public ResponseEntity<UtilisateurDto> create(@RequestBody CreateUtilisateurDto createUtilisateurDto) throws FunctionalException {
            UtilisateurDto createAccount = utilisateurService.create(createUtilisateurDto);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createAccount.login())
                    .toUri();
            return ResponseEntity.created(location).body(createAccount);
    }

    @GetMapping("/utilisateurs/{login}")
    @PreAuthorize("#login.equals(authentication.name)")
    public ResponseEntity<UtilisateurDto> fetchById(@PathVariable String login) {
        try {
            return  ResponseEntity.ok(utilisateurService.fetchByLogin(login));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/utilisateurs")
    public ResponseEntity<ArrayList<UtilisateurDto>> fetchAll() throws FunctionalException {
            return  ResponseEntity.ok(utilisateurService.fetchAll());
    }


}
