package fr.apitodolist.apitodolist.repository;

import fr.apitodolist.apitodolist.modele.ToDoList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IToDoListRepository extends CrudRepository<ToDoList, Long> {

}
