package dtos.user;

import java.io.Serializable;

public record UserDataDto(String email, String firstName, String lastName, boolean isBlacklisted, boolean isAdmin) implements Serializable
{
}
