package dtos.user;

public abstract class ViewUsers {
    public record Request(int pageIndex, int pageSize, String firstNameContains){}
    public record UserDto(String email, String firstName, String lastName){}
}

