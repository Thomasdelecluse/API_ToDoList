package fr.apitodolist.apitodolist.dto.todo;

public record UpdateTodoDto(String title, String description, String type, Boolean status) {
}
