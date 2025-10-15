package goralczyk.maciej.dto.user.function;

import goralczyk.maciej.dto.user.PatchUserRequest;
import goralczyk.maciej.entity.User;

import java.util.function.BiFunction;

public class UpdateUserWithRequestFunction implements BiFunction<User, PatchUserRequest, User> {
    @Override
    public User apply(User user, PatchUserRequest patchUserRequest) {
        return User.builder()
                .id(user.getId())
                .name(patchUserRequest.getName())
                .dateOfBirth(user.getDateOfBirth())
                .role(user.getRole())
                .matches(user.getMatches())
                .photo(user.getPhoto())
                .build();
    }
}
