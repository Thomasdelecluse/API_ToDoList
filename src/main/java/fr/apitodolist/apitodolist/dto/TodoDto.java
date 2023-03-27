package fr.apitodolist.apitodolist.dto;


public record TodoDto(Long id, String title, String description, String type, boolean status) {
}

