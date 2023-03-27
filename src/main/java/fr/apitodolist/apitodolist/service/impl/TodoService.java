package fr.apitodolist.apitodolist.service.impl;

import fr.apitodolist.apitodolist.dto.CreateTodoDto;
import fr.apitodolist.apitodolist.dto.TodoDto;
import fr.apitodolist.apitodolist.dto.UpdateTodoDto;
import fr.apitodolist.apitodolist.modele.Todo;
import fr.apitodolist.apitodolist.repository.ITodoRepository;
import fr.apitodolist.apitodolist.service.ITodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.logging.Logger;


@Service
public class TodoService implements ITodoService {
    // Récupération de notre logger.
    private static final Logger LOGGER = Logger.getLogger( "Compo1" );
    @Autowired
    private ITodoRepository toDoListRepository;

    public TodoDto create(CreateTodoDto createTodoDto) {
        Todo todo = new Todo();
        todo.setTitle(createTodoDto.title());
        todo.setDescription(createTodoDto.description());
        todo.setType(createTodoDto.type());
        todo.setStatus(false);
        LOGGER.info("Création de la todololist " + todo.getId() +" "+ todo.getTitle());
        todo = toDoListRepository.save(todo);
        return new TodoDto(todo.getId(), todo.getTitle(), todo.getDescription(), todo.getType(), todo.getStatus());
    }

    public TodoDto fetchById(long id) {
       Todo list = toDoListRepository.findById(id).orElseThrow();
       return new TodoDto(list.getId(), list.getTitle(), list.getDescription(), list.getType(), list.getStatus());
    }
    public ArrayList<TodoDto> fetchAll() {
        Iterable<Todo> list = toDoListRepository.findAll();

        ArrayList<TodoDto> listOfTodo = new ArrayList<>();

        for(Todo todo : list) {
            TodoDto createTodoDto = new TodoDto(todo.getId(), todo.getTitle(), todo.getDescription(), todo.getType(), todo.getStatus());
            listOfTodo.add(createTodoDto);
        }
        LOGGER.info("Récupération de tout les todo : " + listOfTodo);
        return listOfTodo;
    }

    public TodoDto updateById(long id, UpdateTodoDto updateTodoDto) {
        Todo todo = toDoListRepository.findById(id).orElseThrow();
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
        LOGGER.info("Modification de la tâche " + id + " avec la valeur " + updateTodoDto.status());
        return new TodoDto(todo.getId(), todo.getTitle(), todo.getDescription(), todo.getType(), todo.getStatus());
    }

    public void deleteById(long id) {
        toDoListRepository.deleteById(id);
        LOGGER.info("Suppression de la tâche " + id);
    }
}
