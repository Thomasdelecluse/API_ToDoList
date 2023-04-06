package fr.apitodolist.apitodolist.dto.register;

public record UtilisateurDto(Long id, String login, String password, boolean isAdmin) {
}
