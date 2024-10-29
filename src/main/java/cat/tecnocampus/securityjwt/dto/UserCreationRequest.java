package cat.tecnocampus.securityjwt.dto;

public record UserCreationRequest(String username, String email, String password, String role) {
}
