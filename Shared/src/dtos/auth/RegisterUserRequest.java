package dtos.auth;

public record RegisterUserRequest(String email,String password, String firstName, String lastName) {
}
