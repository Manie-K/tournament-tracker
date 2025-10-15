package goralczyk.maciej.dto.user.function;

import goralczyk.maciej.dto.user.GetUsersResponse;
import goralczyk.maciej.entity.User;

import java.util.List;
import java.util.function.Function;

public class UsersToResponseFunction implements Function<List<User>, GetUsersResponse> {
    @Override
    public GetUsersResponse apply(List<User> users) {
        return GetUsersResponse.builder()
                .users(users.stream()
                        .map(user -> GetUsersResponse.SingleUser.builder()
                                .id(user.getId())
                                .name(user.getName())
                                .build())
                        .toList())
                .build();

    }
}
