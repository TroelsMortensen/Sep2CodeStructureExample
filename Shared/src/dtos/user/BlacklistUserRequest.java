package dtos.user;

import java.io.Serializable;

public record BlacklistUserRequest(String email, String reason) implements Serializable
{
}
