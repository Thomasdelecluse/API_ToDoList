package fr.apitodolist.apitodolist.controller;

import fr.apitodolist.apitodolist.modele.ToDoList;
import fr.apitodolist.apitodolist.service.impl.ToDoListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ToDoListControler {

    private final ToDoListService toDoListService;

    ToDoListControler(ToDoListService toDoListService) {
        this.toDoListService = toDoListService;
    }

    @PostMapping("/create/todolist/{title}/{description}/{type}")
    public String createToDoList(@PathVariable String title, @PathVariable String description, @PathVariable String type) {
            toDoListService.createToDoList(title, description, type);
            return "ToDoList created";
    }

    @GetMapping("/show/todolist/{id}")
    public ToDoList ShowToDoList(@PathVariable Long id) {
        return toDoListService.showToDoList(id);
    }
}
