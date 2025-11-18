package goralczyk.maciej.dto.user.function;

import goralczyk.maciej.dto.user.PatchUserRequest;
import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.service.match.MatchService;

import java.util.List;
import java.util.function.BiFunction;

public class UpdateUserWithRequestFunction implements BiFunction<User, PatchUserRequest, User>
{
    private final MatchService matchService;

    public UpdateUserWithRequestFunction(MatchService matchService) {
        this.matchService = matchService;
    }

    @Override
    public User apply(User user, PatchUserRequest patchUserRequest)
    {
        List<Match> matches = matchService.findAllByUser(user.getId());
        return User.builder()
                .id(user.getId())
                .name(patchUserRequest.getName())
                .login(patchUserRequest.getLogin())
                .email(patchUserRequest.getEmail())
                .password(user.getPassword())
                .dateOfBirth(user.getDateOfBirth())
                .role(user.getRole())
                .matches(matches)
                //.photo(user.getPhoto())
                .build();
    }
}
