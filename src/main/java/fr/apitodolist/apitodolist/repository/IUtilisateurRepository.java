package fr.apitodolist.apitodolist.repository;

import fr.apitodolist.apitodolist.modele.Utilisateur;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUtilisateurRepository  extends CrudRepository<Utilisateur, Long> {
    Utilisateur findByLogin(String login);

}
