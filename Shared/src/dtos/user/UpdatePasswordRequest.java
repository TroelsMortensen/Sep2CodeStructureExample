package dtos.user;

import java.io.Serializable;

public record UpdatePasswordRequest(String email, String oldPassword, String newPassword) implements Serializable
{
}
