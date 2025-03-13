package dtos.user;

// I have a wrapper abstract class, because the request and dto belong together.
// This is a way to have them together in the same file.
// If you plan on re-using the UserDto other places, maybe it should be moved to its own file.
public abstract class ViewUsers {
    public record Request(int pageIndex, int pageSize, String firstNameContains){}
    public record UserDto(String email, String firstName, String lastName){}
}

