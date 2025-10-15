package goralczyk.maciej.dto.user.function;

import goralczyk.maciej.dto.user.GetUserResponse;
import goralczyk.maciej.entity.User;

import java.util.function.Function;

public class UserToResponseFunction implements Function<User, GetUserResponse> {
    @Override
    public GetUserResponse apply(User user) {
        return GetUserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .role(user.getRole().toString())
                .dateOfBirth(user.getDateOfBirth())
                .matches(user.getMatches().stream()
                        .map(match -> GetUserResponse.MatchSummary.builder()
                                .id(match.getId())
                                .build())
                        .toList())
                .build();
    }
}
