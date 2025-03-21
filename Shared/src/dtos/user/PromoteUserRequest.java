package dtos.user;

import java.io.Serializable;

public record PromoteUserRequest(String email) implements Serializable
{
}
