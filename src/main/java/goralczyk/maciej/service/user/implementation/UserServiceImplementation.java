package goralczyk.maciej.service.user.implementation;


import goralczyk.maciej.entity.User;
import goralczyk.maciej.repository.user.api.UserRepository;
import goralczyk.maciej.service.user.api.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for all business actions regarding user.
 */

public class UserServiceImplementation implements UserService
{
    /**
     * Repository for user.
     */
    private final UserRepository userRepository;

    /**
     * @param userRepository repository for user entity
     */
    public UserServiceImplementation(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> find(UUID id) {
        return userRepository.find(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void create(User user) {
        userRepository.create(user);
    }

    @Override
    public void update(User user) {
        userRepository.update(user);
    }

    @Override
    public void delete(UUID id) {
        userRepository.delete(userRepository.find(id).orElseThrow());
    }

    @Override
    public Optional<byte[]> getPhoto(UUID id) {
        return userRepository.find(id).orElseThrow().getPhoto();
    }

    @Override
    public void createPhoto(UUID id, InputStream is) {
        userRepository.find(id).ifPresent(user -> {
            try {
                user.setPhoto(Optional.of(is.readAllBytes()));
                userRepository.update(user);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    @Override
    public void updatePhoto(UUID id, InputStream is) {
        userRepository.find(id).ifPresent(user -> {
            try {
                user.setPhoto(Optional.of(is.readAllBytes()));
                userRepository.update(user);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    @Override
    public void deletePhoto(UUID id) {
        userRepository.find(id).ifPresent(user -> {
            user.setPhoto(Optional.empty());
            userRepository.update(user);
        });
    }
}
