package goralczyk.maciej.controller.user.implementation.rest;

import goralczyk.maciej.controller.user.api.UserController;
import goralczyk.maciej.dto.DtoFunctionFactory;
import goralczyk.maciej.dto.user.GetUserResponse;
import goralczyk.maciej.dto.user.GetUsersResponse;
import goralczyk.maciej.dto.user.PatchUserRequest;
import goralczyk.maciej.dto.user.PutUserRequest;
import goralczyk.maciej.service.user.UserService;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Path("")
public class UserRestController implements UserController
{
    /**
     * User service.
     */
    private final UserService service;

    /**
     * Factory producing functions for conversion between DTO and entities.
     */
    private final DtoFunctionFactory factory;


    /**
     * Allows to create {@link UriBuilder} based on current request.
     */
    private final UriInfo uriInfo;

    /**
     * Current HTTP Servlet response.
     */
    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        //ATM in this implementation only HttpServletRequest can be injected with CDI so JAX-RS injection is used.
        this.response = response;
    }

    /**
     * @param service user service
     * @param factory factory producing functions for conversion between DTO and entities
     */
    @Inject
    public UserRestController(UserService service, DtoFunctionFactory factory, UriInfo uriInfo) {
        this.service = service;
        this.factory = factory;
        this.uriInfo = uriInfo;
    }


    @Override
    public GetUsersResponse getUsers() {
        return factory.usersToResponse().apply(service.findAll());
    }

    @Override
    public GetUserResponse getUser(UUID uuid) {
        return service.find(uuid)
                .map(factory.userToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putUser(UUID id, PutUserRequest request) {
        service.create(factory.requestToUser().apply(id, request));
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader("Location", uriInfo.getAbsolutePath().toString() + "/" + id);
    }

    @Override
    public void patchUser(UUID id, PatchUserRequest request) {
        service.update(factory.updateUser().apply(service.find(id).orElseThrow(NotFoundException::new), request));
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    public void deleteUser(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public Optional<byte[]> getUserPhoto(UUID id) {
        System.out.println("Not implemented yet");
        return Optional.empty();
    }

    @Override
    public void putUserPhoto(UUID id, InputStream photo) {
        System.out.println("Not implemented yet");
    }

    @Override
    public void patchUserPhoto(UUID id, InputStream photo) {
        System.out.println("Not implemented yet");
    }

    @Override
    public void deleteUserPhoto(UUID id) {
        System.out.println("Not implemented yet");
    }
}
