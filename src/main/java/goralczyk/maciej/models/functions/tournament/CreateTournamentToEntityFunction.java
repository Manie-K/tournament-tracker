package goralczyk.maciej.models.functions.tournament;

import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.models.tournament.TournamentCreateModel;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

public class CreateTournamentToEntityFunction implements Function<TournamentCreateModel, Tournament>, Serializable {

    @Override
    public Tournament apply(TournamentCreateModel tournamentCreateModel) {
        return Tournament.builder()
                .id(tournamentCreateModel.getId())
                .name(tournamentCreateModel.getName())
                .location(tournamentCreateModel.getLocation())
                .matches(List.of())
                .build();
    }
}
