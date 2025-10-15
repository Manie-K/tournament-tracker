package goralczyk.maciej.dto;

import goralczyk.maciej.dto.user.GetUserResponse;
import goralczyk.maciej.dto.user.GetUsersResponse;
import goralczyk.maciej.dto.user.PutUserRequest;
import goralczyk.maciej.dto.user.function.RequestToUserFunction;
import goralczyk.maciej.dto.user.function.UpdateUserWithRequestFunction;
import goralczyk.maciej.dto.user.function.UserToResponseFunction;
import goralczyk.maciej.dto.user.function.UsersToResponseFunction;
import goralczyk.maciej.entity.User;

import java.util.function.Function;

/**
 * Factory for creating {@link Function} implementation for converting between various objects used in different layers.
 * Instead of injecting multiple function objects single factory is injected.
 */

public class DtoFunctionFactory
{
    /**
     * Returns a function to convert a {@link PutUserRequest} to a {@link User}.
     *
     * @return RequestToUserFunction instance
     */
    public RequestToUserFunction requestToUser() {
        return new RequestToUserFunction();
    }

    /**
     * Returns a function to update a {@link User}.
     *
     * @return UpdateUserFunction instance
     */
    public UpdateUserWithRequestFunction updateUser() {
        return new UpdateUserWithRequestFunction();
    }


    /**
     * Returns a function to convert a list of {@link User} to {@link GetUsersResponse}.
     *
     * @return UsersToResponseFunction instance
     */
    public UsersToResponseFunction usersToResponse() {
        return new UsersToResponseFunction();
    }

    /**
     * Returns a function to convert a single {@link User} to {@link GetUserResponse}.
     *
     * @return UserToResponseFunction instance
     */
    public UserToResponseFunction userToResponse() {
        return new UserToResponseFunction();
    }

}
