package dtos.user;

import java.util.List;

public abstract class ViewUsers {
    public record Request(int pageIndex, int pageSize, String emailContains, String firstNameContains, String lastNameContains){}
    public record Response(List<UserDto> users){}
    public record UserDto(String email, String firstName, String lastName){}
}

