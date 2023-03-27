package fr.apitodolist.apitodolist.service;


import fr.apitodolist.apitodolist.dto.CreateTodoDto;
import fr.apitodolist.apitodolist.dto.TodoDto;
import fr.apitodolist.apitodolist.modele.Todo;

import java.util.ArrayList;

public interface ITodoService {
    TodoDto create(CreateTodoDto createTodoDto);

    ArrayList<TodoDto> fetchAll();

    TodoDto fetchById(long id);
}

