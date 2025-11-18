package goralczyk.maciej.dto.user.function;

import goralczyk.maciej.dto.user.PutUserRequest;
import goralczyk.maciej.entity.Role;
import goralczyk.maciej.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToUserFunction implements BiFunction<UUID, PutUserRequest, User> {
    @Override
    public User apply(UUID id, PutUserRequest request) {
        return User.builder()
                .id(id)
                .name(request.getName())
                .login(request.getLogin())
                .email(request.getEmail())
                .password(request.getPassword())
                .dateOfBirth(request.getDateOfBirth())
                .role(Role.USER)
                .matches(List.of()) // New user has no matches
                .photo(null) // New user has no photo
                .build();
    }
}
