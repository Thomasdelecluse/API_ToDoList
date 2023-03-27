package fr.apitodolist.apitodolist.controller;

import fr.apitodolist.apitodolist.dto.CreateTodoDto;
import fr.apitodolist.apitodolist.dto.TodoDto;
import fr.apitodolist.apitodolist.dto.UpdateTodoDto;
import fr.apitodolist.apitodolist.modele.Todo;
import fr.apitodolist.apitodolist.service.impl.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;

@RestController
@CrossOrigin
public class TodosController {

    private final TodoService todoService;

    TodosController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/todos")
    public ResponseEntity<TodoDto> create(@RequestBody CreateTodoDto createTodoDto) {
        TodoDto todo = todoService.create(createTodoDto);
        return ResponseEntity.created(URI.create("/todos/" + todo.id())).body(todo);
    }

    @GetMapping("/todos/{id}")
    public TodoDto fetchById(@PathVariable Long id) {
        return todoService.fetchById(id);
    }

    @GetMapping("/todos")
    public ResponseEntity<ArrayList<TodoDto>> fetchAll() {
        return ResponseEntity.ok(todoService.fetchAll());
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<TodoDto> fetchById(@PathVariable long id, @RequestBody UpdateTodoDto toDoList) {
           return ResponseEntity.ok(todoService.updateById(id, toDoList));
    }

    @DeleteMapping ("/todos/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        todoService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
