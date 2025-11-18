package goralczyk.maciej.dto.match.function;

import goralczyk.maciej.dto.match.GetMatchResponse;
import goralczyk.maciej.dto.user.GetUserResponse;
import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.User;

import java.util.function.Function;

public class MatchToResponseFunction implements Function<Match, GetMatchResponse> {
    @Override
    public GetMatchResponse apply(Match match) {
        return GetMatchResponse.builder()
                .id(match.getId())
                .participantA(GetMatchResponse.UserSummary.builder()
                        .name(match.getParticipantA().getName())
                        .build())
                /*.participantB(GetMatchResponse.UserSummary.builder()
                        .name(match.getParticipantB().getName())
                        .build())*/
                .tournament(GetMatchResponse.TournamentSummary.builder()
                        .name(match.getTournament().getName())
                        .build())
                .result(match.getResult())
                .build();
    }
}