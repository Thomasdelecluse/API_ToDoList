package fr.apitodolist.apitodolist.service;


import fr.apitodolist.apitodolist.dto.todo.CreateTodoDto;
import fr.apitodolist.apitodolist.dto.todo.TodoDto;

import java.util.ArrayList;

public interface ITodoService {
    TodoDto create(CreateTodoDto createTodoDto);

    ArrayList<TodoDto> fetchAll();

    TodoDto fetchById(long id);
}

