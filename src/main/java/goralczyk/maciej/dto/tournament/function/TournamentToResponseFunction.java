package goralczyk.maciej.dto.tournament.function;

import goralczyk.maciej.dto.tournament.GetTournamentResponse;
import goralczyk.maciej.dto.user.GetUserResponse;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.entity.User;

import java.util.function.Function;

public class TournamentToResponseFunction implements Function<Tournament, GetTournamentResponse> {
    @Override
    public GetTournamentResponse apply(Tournament tournament) {

        return GetTournamentResponse.builder()
                .id(tournament.getId())
                .name(tournament.getName())
                .location(tournament.getLocation())
                .matches(tournament.getMatches().stream()
                        .map(match -> GetTournamentResponse.MatchSummary.builder()
                                .playerOne(match.getParticipantA().getName())
                                .playerTwo(match.getParticipantB().getName())
                                .build())
                        .toList())
                .build();
    }
}
