package fr.apitodolist.apitodolist.service.impl;

import fr.apitodolist.apitodolist.config.error.FunctionalException;
import fr.apitodolist.apitodolist.dto.todo.CreateTodoDto;
import fr.apitodolist.apitodolist.dto.todo.TodoDto;
import fr.apitodolist.apitodolist.dto.todo.UpdateTodoDto;
import fr.apitodolist.apitodolist.modele.Todo;
import fr.apitodolist.apitodolist.repository.ITodoRepository;
import fr.apitodolist.apitodolist.service.ITodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.logging.Logger;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;


@Service
public class TodoService implements ITodoService {

    // Récupération de notre logger.
    private static final Logger logger = Logger.getLogger( "Compo1" );
    @Autowired
    private ITodoRepository toDoListRepository;

    public TodoDto create(CreateTodoDto createTodoDto, Authentication authentication) {
        Todo todo = new Todo();
        todo.setTitle(createTodoDto.title());
        todo.setName(authentication.getName());
        todo.setDescription(createTodoDto.description());
        todo.setType(createTodoDto.type());
        todo.setStatus(false);
        todo = toDoListRepository.save(todo);
        logger.info("Création de la todololist " + todo.getId() +" "+ todo.getTitle());
        return new TodoDto(todo.getId(), todo.getTitle(), todo.getDescription(), todo.getType(), todo.getStatus());
    }

    public TodoDto fetchById(long id) {
       Todo list = toDoListRepository.findById(id).orElseThrow();
       return new TodoDto(list.getId(), list.getTitle(), list.getDescription(), list.getType(), list.getStatus());
    }
    public ArrayList<TodoDto> fetchAll(Authentication authentication) {
        String name = authentication.getName();
        logger.info("Récupération de tout les todo : " + name);
        Iterable<Todo> list = toDoListRepository.findByName(name);

        ArrayList<TodoDto> listOfTodo = new ArrayList<>();

        for(Todo todo : list) {
            TodoDto createTodoDto = new TodoDto(todo.getId(), todo.getTitle(), todo.getDescription(), todo.getType(), todo.getStatus());
            listOfTodo.add(createTodoDto);
        }
        logger.info("Récupération de tout les todo : " + listOfTodo);
        return listOfTodo;
    }

    public TodoDto updateById(long id, UpdateTodoDto updateTodoDto, Authentication authentication) throws FunctionalException {
        Todo todo = toDoListRepository.findById(id).orElseThrow();
        if(!todo.getName().equals(authentication.getName())){
            throw new FunctionalException(UNAUTHORIZED, "Vous ne pouvez pas modifier le status d'un todos qui n'est pas le vôtre");
        }
        if(updateTodoDto.status() != null) {
            todo.setStatus(updateTodoDto.status());
        }
        if (updateTodoDto.title() != null) {
            todo.setTitle(updateTodoDto.title());
        }
        if (updateTodoDto.description() != null) {
            todo.setDescription(updateTodoDto.description());
        }
        if (updateTodoDto.type() != null) {
            todo.setType(updateTodoDto.type());
        }
        todo = toDoListRepository.save(todo);
        logger.info("Modification de la tâche " + id + " avec la valeur " + updateTodoDto.status());
        return new TodoDto(todo.getId(), todo.getTitle(), todo.getDescription(), todo.getType(), todo.getStatus());
    }

    public void deleteById(long id) {
        toDoListRepository.deleteById(id);
        logger.info("Suppression de la tâche " + id);
    }
}
