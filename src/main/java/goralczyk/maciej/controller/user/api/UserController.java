package goralczyk.maciej.controller.user.api;

import goralczyk.maciej.dto.user.GetUserResponse;
import goralczyk.maciej.dto.user.GetUsersResponse;
import goralczyk.maciej.dto.user.PutUserRequest;
import goralczyk.maciej.dto.user.PatchUserRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.Optional;
import java.util.UUID;
import java.io.InputStream;


/**
 * Controller for managing users.
 */
@Path("")
public interface UserController
{
    /**
     * @return all users representations.
     */
    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    GetUsersResponse getUsers();


    /**
     * @param uuid user's id.
     * @return user representation.
     */
    @GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetUserResponse getUser(@PathParam("id") UUID uuid);

    /**
     * @param id      user's id.
     * @param request new user representation.
     */
    @PUT
    @Path("/users/{id}")
    void putUser(@PathParam("id") UUID id, PutUserRequest request);

    /**
     * @param id      user's id.
     * @param request user update representation.
     */
    @PATCH
    @Path("/users/{id}")
    void patchUser(@PathParam("id") UUID id, PatchUserRequest request);

    /**
     * @param id user's id.
     */
    @DELETE
    @Path("/users/{id}")
    void deleteUser(@PathParam("id") UUID id);



    /**
     * @param id user's id.
     * @return user's photo.
     */
    Optional<byte[]> getUserPhoto(UUID id);

    /**
     * @param id       user's id.
     * @param photo user's new photo.
     */
    void putUserPhoto(UUID id, InputStream photo);

    /**
     * @param id       user's id.
     * @param photo user's updated photo.
     */
    void patchUserPhoto(UUID id, InputStream photo);

    /**
     * @param id       user's id.
     */
    void deleteUserPhoto(UUID id);
}
