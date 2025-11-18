package goralczyk.maciej.service.user;


import goralczyk.maciej.configuration.StringConstants;
import goralczyk.maciej.entity.Role;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.repository.user.api.UserRepository;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
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
@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class UserService
{
    /**
     * Repository for user.
     */
    private final UserRepository userRepository;
    private final Pbkdf2PasswordHash passwordHash;
    private final String photoDir;

    /**
     * @param userRepository repository for user entity
     */
    @Inject
    public UserService(UserRepository userRepository,
                        @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash)
    {
        this.userRepository = userRepository;
        this.passwordHash = passwordHash;
        this.photoDir = "C:/temp/tournament-tracker/photos";//photoDir;
    }

    @RolesAllowed(Role.ADMIN)
    public Optional<User> find(UUID id) {
        return userRepository.find(id);
    }

    @RolesAllowed(Role.ADMIN)
    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @RolesAllowed(Role.ADMIN)
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @RolesAllowed(Role.ADMIN)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @PermitAll
    public void create(User user) {
        if(userRepository.find(user.getId()).isPresent())
        {
            throw new IllegalStateException("User with this ID already exists");
        }
        user.setPassword(passwordHash.generate(user.getPassword().toCharArray()));
        userRepository.create(user);
    }

    @RolesAllowed(Role.USER)
    public void update(User user) {
        userRepository.update(user);
    }

    @RolesAllowed(Role.ADMIN)
    public void delete(UUID id) {
        userRepository.delete(userRepository.find(id).orElseThrow());
    }

    /**
     * @param login    user's login
     * @param password user's password
     * @return true if provided login and password are correct
     */
    @PermitAll
    public boolean verify(String login, String password) {
        return findByLogin(login)
                .map(user -> passwordHash.verify(password.toCharArray(), user.getPassword()))
                .orElse(false);
    }


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
