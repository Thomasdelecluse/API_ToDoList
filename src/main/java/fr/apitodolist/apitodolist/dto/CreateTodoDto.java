package fr.apitodolist.apitodolist.dto;


public record CreateTodoDto(String title, String description, String type, boolean status) {
}

