package fr.apitodolist.apitodolist.dto.todo;


public record CreateTodoDto(String title, String name, String description, String type, boolean status) {
}

