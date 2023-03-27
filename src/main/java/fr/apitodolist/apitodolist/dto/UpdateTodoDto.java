package fr.apitodolist.apitodolist.dto;

public record UpdateTodoDto(String title, String description, String type, Boolean status) {
}
