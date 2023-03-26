package fr.apitodolist.apitodolist.service.impl;

import fr.apitodolist.apitodolist.modele.ToDoList;
import fr.apitodolist.apitodolist.repository.IToDoListRepository;
import fr.apitodolist.apitodolist.service.IToDoListInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ToDoListService implements IToDoListInterface {

    @Autowired
    private IToDoListRepository toDoListRepository;

    public void createToDoList(String title, String description, String type) {
        ToDoList toDoList = new ToDoList();
        toDoList.setTitle(title);
        toDoList.setDescription(description);
        toDoList.setType(type);
        toDoList.setStatus(false);
        toDoListRepository.save(toDoList);
    }

    public ToDoList showToDoList(long id) {
       ToDoList list = toDoListRepository.findById(id).orElseThrow();
       return list;
    }
}
