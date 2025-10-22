package goralczyk.maciej.dto.tournament.function;

import goralczyk.maciej.dto.tournament.GetTournamentsResponse;
import goralczyk.maciej.entity.Tournament;

import java.util.List;
import java.util.function.Function;

public class TournamentsToResponseFunction implements Function<List<Tournament>, GetTournamentsResponse> {

    @Override
    public GetTournamentsResponse apply(List<Tournament> tournaments) {
        return GetTournamentsResponse.builder()
                .tournaments(tournaments.stream().map(
                        tournament -> GetTournamentsResponse.SingleTournament.builder()
                                .name(tournament.getName())
                                .location(tournament.getLocation())
                                .build(
                )).toList())
                .build();
    }
}
