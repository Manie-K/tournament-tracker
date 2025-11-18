package goralczyk.maciej.dto.user.function;

import goralczyk.maciej.dto.user.GetUserResponse;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.service.match.MatchService;

import java.util.List;
import java.util.function.Function;

public class UserToResponseFunction implements Function<User, GetUserResponse>
{
    private final MatchService matchService;

    public UserToResponseFunction(MatchService matchService) {
        this.matchService = matchService;
    }

    @Override
    public GetUserResponse apply(User user)
    {
        List<GetUserResponse.MatchSummary> matches = matchService.findAllByUser(user.getId()).stream()
                .map(match -> GetUserResponse.MatchSummary.builder()
                        .id(match.getId())
                        .build())
                .toList();

        return GetUserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .login(user.getLogin())
                .email(user.getEmail())
                .role(user.getRole())
                .dateOfBirth(user.getDateOfBirth())
                .matches(matches)
                .build();
    }
}
