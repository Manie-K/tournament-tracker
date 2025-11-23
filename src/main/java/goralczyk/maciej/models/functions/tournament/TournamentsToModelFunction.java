package goralczyk.maciej.models.functions.tournament;

import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.models.match.MatchesModel;
import goralczyk.maciej.models.tournament.TournamentModel;
import goralczyk.maciej.models.tournament.TournamentsModel;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

public class TournamentsToModelFunction implements Function<List<Tournament>, TournamentsModel>, Serializable
{

    @Override
    public TournamentsModel apply(List<Tournament> tournamentList) {
        return TournamentsModel.builder()
                .tournaments(tournamentList
                        .stream()
                        .map(t ->
                                TournamentsModel.Tournament.builder()
                                        .id(t.getId())
                                        .name(t.getName())
                                        .build()
                        )
                        .toList()
                )
                .build();
    }
}
