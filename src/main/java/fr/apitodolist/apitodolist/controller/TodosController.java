package fr.apitodolist.apitodolist.controller;

import fr.apitodolist.apitodolist.config.error.FunctionalExeption;
import fr.apitodolist.apitodolist.dto.todo.CreateTodoDto;
import fr.apitodolist.apitodolist.dto.todo.TodoDto;
import fr.apitodolist.apitodolist.dto.todo.UpdateTodoDto;
import fr.apitodolist.apitodolist.service.impl.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;

@RestController
@CrossOrigin
public class TodosController {

    private final TodoService todoService;

    TodosController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/todos")
    public ResponseEntity<TodoDto> create(@RequestBody CreateTodoDto createTodoDto,Authentication authentication) {
        TodoDto todo = todoService.create(createTodoDto, authentication);
        return ResponseEntity.created(URI.create("/todos/" + todo.id())).body(todo);
    }

    @GetMapping("/todos/{id}")
    public TodoDto fetchById(@PathVariable Long id) {
        return todoService.fetchById(id);
    }


    @GetMapping("/todos")
    public ResponseEntity<ArrayList<TodoDto>> fetchAll(Authentication authentication) {
        return ResponseEntity.ok(todoService.fetchAll(authentication));
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<TodoDto> fetchById(@PathVariable long id, @RequestBody UpdateTodoDto toDoList, Authentication authentication) throws FunctionalExeption {
            return ResponseEntity.ok(todoService.updateById(id, toDoList, authentication));
    }

    @DeleteMapping ("/todos/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        todoService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping ("/test")
    public String test(Principal principal) {
        return "vous etes " + principal.getName() ;
    }
}
