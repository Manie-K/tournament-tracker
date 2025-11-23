package goralczyk.maciej.models.functions.tournament;

import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.models.tournament.TournamentEditModel;

import java.io.Serializable;
import java.util.function.BiFunction;

public class EditTournamentToTournamentFunction implements BiFunction<Tournament, TournamentEditModel, Tournament>, Serializable {
    @Override
    public Tournament apply(Tournament tournament, TournamentEditModel editTournamentModel) {
        return Tournament.builder()
                .id(tournament.getId())
                .location(editTournamentModel.getLocation())
                .name(editTournamentModel.getName())
                .matches(tournament.getMatches())
                .build();
    }
}
