package goralczyk.maciej.service.user.implementation;


import goralczyk.maciej.configuration.StringConstants;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.repository.user.api.UserRepository;
import goralczyk.maciej.service.user.api.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.BadRequestException;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for all business actions regarding user.
 */
@ApplicationScoped
@NoArgsConstructor(force = true)
public class UserServiceImplementation implements UserService
{
    /**
     * Repository for user.
     */
    private final UserRepository userRepository;
    private final String photoDir;

    /**
     * @param userRepository repository for user entity
     */
    @Inject
    public UserServiceImplementation(UserRepository userRepository)//, @Named("photoDirectory") String photoDir)
    {
        this.userRepository = userRepository;
        this.photoDir = "C:/temp/tournament-tracker/photos";//photoDir;
    }

    @Override
    public Optional<User> find(UUID id) {
        return userRepository.find(id);
    }

    @Override
    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
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
        //TEMP, while we store it in files not db
        Path path = GetUserPhotoPath(id);
        try {
            if (Files.exists(path)) {
                return Optional.of(Files.readAllBytes(path));
            } else {
                return Optional.empty();
            }
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }

        /*
        byte[] photo = userRepository.find(id).orElseThrow().getPhoto();
        if (photo == null || photo.length == 0) {
            return Optional.empty();
        }

        return Optional.of(photo);
        */
    }


    @Override
    public void createPhoto(UUID id, InputStream is) {
        userRepository.find(id).ifPresent(user -> {
            try {
                if(user.getPhoto() != null) {
                    throw new BadRequestException("Photo already exists. Use patch to update.");
                }
                byte[] photoBytes = is.readAllBytes();
                user.setPhoto(photoBytes);
                userRepository.update(user);

                //TEMP, while we store it in files not db
                Path path = GetUserPhotoPath(id);
                Files.write(path, user.getPhoto(), StandardOpenOption.CREATE_NEW);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    @Override
    public void updatePhoto(UUID id, InputStream is) {
        userRepository.find(id).ifPresent(user -> {
            try {
                byte[] photoBytes = is.readAllBytes();
                user.setPhoto(photoBytes);
                userRepository.update(user);

                //TEMP, while we store it in files not db
                Path path = GetUserPhotoPath(id);
                Files.write(path, user.getPhoto(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    @Override
    public void deletePhoto(UUID id) {
        userRepository.find(id).ifPresent(user -> {
            user.setPhoto(null);
            userRepository.update(user);

            //TEMP, while we store it in files not db
            Path path = GetUserPhotoPath(id);
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Path GetUserPhotoPath(UUID id) {
        return Paths.get(photoDir, id.toString() + StringConstants.PHOTO_EXT);
    }
}
