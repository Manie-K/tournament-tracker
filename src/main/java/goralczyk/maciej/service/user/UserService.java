package goralczyk.maciej.service.user;


import goralczyk.maciej.entity.User;
import goralczyk.maciej.repository.user.api.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for all business actions regarding user.
 */

public class UserService
{
    /**
     * Repository for user.
     */
    private final UserRepository userRepository;

    /**
     * @param userRepository repository for user entity
     */
    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    /**
     * Finds single user.
     *
     * @param id user's id.
     * @return container with user.
     */
    public Optional<User> find(UUID id) {
        return userRepository.find(id);
    }

    /**
     * @return all available users.
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Creates new user.
     *
     * @param user user.
     */
    public void create(User user) {
        userRepository.create(user);
    }

    /**
     * Updates existing user.
     *
     * @param user user to be updated
     */
    public void update(User user) {
        userRepository.update(user);
    }

    /**
     * Deletes existing user.
     *
     * @param id existing user's id to be deleted
     */
    public void delete(UUID id) {
        userRepository.delete(userRepository.find(id).orElseThrow());
    }

    /**
     * Updates photo of the user.
     *
     * @param id user's id.
     * @param is input stream containing new photo.
     */
    public void updatePhoto(UUID id, InputStream is) {
        userRepository.find(id).ifPresent(user -> {
            try {
                user.setPhoto(is.readAllBytes());
                userRepository.update(user);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }
}
