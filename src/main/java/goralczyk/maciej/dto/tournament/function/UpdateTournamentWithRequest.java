package goralczyk.maciej.dto.tournament.function;


import goralczyk.maciej.dto.tournament.PatchTournamentRequest;
import goralczyk.maciej.entity.Tournament;

import java.util.function.BiFunction;

public class UpdateTournamentWithRequest implements BiFunction<Tournament, PatchTournamentRequest, Tournament> {
    @Override
    public Tournament apply(Tournament tournament, PatchTournamentRequest patchTournamentRequest) {
        return Tournament.builder()
                .id(tournament.getId())
                .name(patchTournamentRequest.getName())
                .location(patchTournamentRequest.getLocation())
                .matches(tournament.getMatches())
                .build();
    }
}
