package dtos.user;

public record UpdatePasswordRequest(String email, String oldPassword, String newPassword) {
}
