package goralczyk.maciej.service.user.api;

import goralczyk.maciej.entity.User;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService
{
    /**
     * Finds single user.
     *
     * @param id user's id.
     * @return container with user.
     */
    Optional<User> find(UUID id);

    /**
     * Finds single user by username.
     *
     * @param name user's name.
     * @return container with user.
     */
    Optional<User> findByName(String name);

    /**
     * @return all available users.
     */
    List<User> findAll();

    /**
     * Creates new user.
     *
     * @param user user.
     */
    void create(User user);

    /**
     * Updates existing user.
     *
     * @param user user to be updated
     */
    void update(User user);

    /**
     * Deletes existing user.
     *
     * @param id existing user's id to be deleted
     */
    void delete(UUID id);

    /**
     * Gets photo of the user.
     *
     * @param id user's id.
     * @return array of bytes representing photo.
     */
    Optional<byte[]> getPhoto(UUID id);

    /**
     * Creates photo of the user.
     *
     * @param id user's id.
     * @param is input stream containing photo.
     */
    void createPhoto(UUID id, InputStream is);

    /**
     * Updates photo of the user.
     *
     * @param id user's id.
     * @param is input stream containing new photo.
     */
    void updatePhoto(UUID id, InputStream is);

    /**
     * Deletes photo of the user.
     *
     * @param id user's id.
     */
    void deletePhoto(UUID id);
}
