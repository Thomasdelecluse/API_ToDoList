package fr.apitodolist.apitodolist.dto.todo;


public record TodoDto(Long id, String title, String description, String type, boolean status) {
}

