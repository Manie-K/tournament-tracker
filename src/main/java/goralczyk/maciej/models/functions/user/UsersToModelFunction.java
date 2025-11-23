package goralczyk.maciej.models.functions.user;

import goralczyk.maciej.entity.User;
import goralczyk.maciej.models.user.UsersModel;

import java.util.List;
import java.util.function.Function;


public class UsersToModelFunction implements Function<List<User>, UsersModel> {
    @Override
    public UsersModel apply(List<User> users) {
        return UsersModel.builder()
                .users(users.stream()
                        .map(u ->
                                UsersModel.User.builder()
                                    .id(u.getId())
                                    .login(u.getLogin())
                                    .build())
                        .toList())
                .build();
    }
}
