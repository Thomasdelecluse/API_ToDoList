package fr.apitodolist.apitodolist.service;


import fr.apitodolist.apitodolist.dto.todo.CreateTodoDto;
import fr.apitodolist.apitodolist.dto.todo.TodoDto;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;

public interface ITodoService {
    TodoDto create(CreateTodoDto createTodoDto, Authentication authentication);

    TodoDto fetchById(long id);
}

