package fr.apitodolist.apitodolist.repository;

import fr.apitodolist.apitodolist.modele.Todo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITodoRepository extends CrudRepository<Todo, Long> {

}
