package goralczyk.maciej.models.functions.tournament;

import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.models.tournament.TournamentEditModel;

import java.io.Serializable;
import java.util.function.Function;

public class TournamentToEditModelFunction implements Function<Tournament, TournamentEditModel>, Serializable {
    @Override
    public TournamentEditModel apply(Tournament tournament) {
        return TournamentEditModel.builder()
                .location(tournament.getLocation())
                .name(tournament.getName())
                .build();
    }
}
