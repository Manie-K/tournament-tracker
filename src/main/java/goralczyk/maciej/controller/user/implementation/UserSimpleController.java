package goralczyk.maciej.controller.user.implementation;

import goralczyk.maciej.controller.servlet.exception.*;
import goralczyk.maciej.controller.user.api.UserController;
import goralczyk.maciej.dto.DtoFunctionFactory;
import goralczyk.maciej.dto.user.*;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.service.user.api.UserService;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

public class UserSimpleController implements UserController
{
    /**
     * User service.
     */
    private final UserService service;

    /**
     * Factory producing functions for conversion between DTO and entities.
     */
    private final DtoFunctionFactory dtoFactory;

    /**
     * @param service user service
     * @param factory factory producing functions for conversion between DTO and entities
     */
    public UserSimpleController(UserService service, DtoFunctionFactory factory) {
        this.service = service;
        this.dtoFactory = factory;
    }

    @Override
    public GetUsersResponse getUsers()
    {
        return dtoFactory.usersToResponse().apply(service.findAll());
    }

    @Override
    public GetUserResponse getUser(UUID uuid)
    {
        return service.find(uuid)
                .map(dtoFactory.userToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putUser(UUID id, PutUserRequest request)
    {
        try {
            service.create(dtoFactory.requestToUser().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void patchUser(UUID id, PatchUserRequest request)
    {
        service.find(id).ifPresentOrElse(
                entity -> service.update(dtoFactory.updateUser().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteUser(UUID id)
    {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );

    }

    @Override
    public Optional<byte[]> getUserPhoto(UUID id) {
        return service.find(id)
                .orElseThrow(NotFoundException::new)
                .getPhoto();
    }

    @Override
    public void putUserPhoto(UUID id, InputStream portrait) {
        service.find(id).ifPresentOrElse(
                u -> service.createPhoto(id, portrait),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void patchUserPhoto(UUID id, InputStream portrait) {
        service.find(id).ifPresentOrElse(
                u -> service.updatePhoto(id, portrait),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteUserPhoto(UUID id) {
        service.find(id).ifPresentOrElse(
                u -> service.deletePhoto(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
