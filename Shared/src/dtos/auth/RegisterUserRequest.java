package dtos.auth;

import java.io.Serializable;

public record RegisterUserRequest(String email, String password, String firstName, String lastName) implements Serializable
{
}
