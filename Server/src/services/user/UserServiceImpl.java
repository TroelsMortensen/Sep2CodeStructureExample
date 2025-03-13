package services.user;

import dtos.user.BlacklistUserRequest;
import dtos.user.PromoteUserToAdminRequest;
import model.entities.User;
import model.exceptions.BusinessLogicException;
import persistence.repositories.user.UserListRepository;

public class UserServiceImpl implements UserService {
    private final UserListRepository userRepo;

    public UserServiceImpl(UserListRepository userRepository) {
        userRepo = userRepository;
    }

    @Override
    public void promoteToAdmin(PromoteUserToAdminRequest request) {
        User user = userRepo.getSingle(request.email());
        if (user.isBlacklisted()) {
            throw new BusinessLogicException("The user with email '" + request.email() + "' is blacklisted, and cannot be promoted to admin.");
        }

        if (user.isAdmin()) {
            throw new BusinessLogicException("The user with email '" + request.email() + "' is already an admin.");
        }

        user.setAdmin(true);
        userRepo.save(user);
    }

    @Override
    public void blacklistUser(BlacklistUserRequest request) {
        User user = userRepo.getSingle(request.email());
        if (user.isBlacklisted()) {
            throw new BusinessLogicException("The user with email '" + request.email() + "' is already blacklisted.");
        }
        user.setBlacklisted(true);
        user.setAdmin(false);
        userRepo.save(user);
    }
}
