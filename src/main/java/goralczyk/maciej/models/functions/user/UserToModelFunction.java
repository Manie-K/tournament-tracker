package goralczyk.maciej.models.functions.user;

import goralczyk.maciej.entity.User;
import goralczyk.maciej.models.user.UserModel;

import java.io.Serializable;
import java.util.function.Function;

public class UserToModelFunction implements Function<User, UserModel>, Serializable {

    @Override
    public UserModel apply(User entity) {
        return UserModel.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .build();
    }

}
