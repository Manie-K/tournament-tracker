package goralczyk.maciej.controller.user.api;

import goralczyk.maciej.dto.user.GetUserResponse;
import goralczyk.maciej.dto.user.GetUsersResponse;
import goralczyk.maciej.dto.user.PutUserRequest;
import goralczyk.maciej.dto.user.PatchUserRequest;

import java.util.UUID;
import java.io.InputStream;


/**
 * Controller for managing users.
 */

public interface UserController
{
    /**
     * @return all users representations.
     */
    GetUsersResponse getUsers();


    /**
     * @param uuid user's id.
     * @return user representation.
     */
    GetUserResponse getUser(UUID uuid);

    /**
     * @param id      user's id.
     * @param request new user representation.
     */
    void putUser(UUID id, PutUserRequest request);

    /**
     * @param id      user's id.
     * @param request user update representation.
     */
    void patchUser(UUID id, PatchUserRequest request);

    /**
     * @param id user's id.
     */
    void deleteUser(UUID id);

    /**
     * @param id user's id.
     * @return user's photo.
     */
    byte[] getUserPhoto(UUID id);

    /**
     * @param id       user's id.
     * @param portrait user's new photo.
     */
    void putUserPhoto(UUID id, InputStream portrait);

}
