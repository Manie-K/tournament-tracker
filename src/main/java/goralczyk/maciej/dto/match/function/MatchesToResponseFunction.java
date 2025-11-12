package goralczyk.maciej.dto.match.function;

import goralczyk.maciej.dto.match.GetMatchesResponse;
import goralczyk.maciej.dto.user.GetUsersResponse;
import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.User;

import java.util.List;
import java.util.function.Function;

public class MatchesToResponseFunction implements Function<List<Match>, GetMatchesResponse>
{
    @Override
    public GetMatchesResponse apply(List<Match> matches) {
        return GetMatchesResponse.builder()
                .matches(matches.stream()
                        .map(match -> GetMatchesResponse.SingleMatch.builder()
                                .id(match.getId())
                                /*.participantA(GetMatchesResponse.UserSummary.builder()
                                        .name(match.getParticipantA().getName())
                                        .build())
                                .participantB(GetMatchesResponse.UserSummary.builder()
                                        .name(match.getParticipantB().getName())
                                        .build())*/
                                .build())
                        .toList())
                .build();
    }
}
