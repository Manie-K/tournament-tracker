package goralczyk.maciej.dto.tournament.function;


import goralczyk.maciej.dto.tournament.PatchTournamentRequest;
import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.service.match.api.MatchService;
import jakarta.inject.Inject;

import java.util.List;
import java.util.function.BiFunction;

public class UpdateTournamentWithRequest implements BiFunction<Tournament, PatchTournamentRequest, Tournament> {

    private final MatchService matchService;

    public UpdateTournamentWithRequest(MatchService matchService) {this.matchService = matchService;}

    @Override
    public Tournament apply(Tournament tournament, PatchTournamentRequest patchTournamentRequest) {
        System.out.println("Updating tournament " + tournament.getId());
        System.out.println("Request: " + patchTournamentRequest);

        List<Match> matches = matchService.findAllByTournament(tournament.getId());

        return Tournament.builder()
                .id(tournament.getId())
                .name(patchTournamentRequest.getName())
                .location(patchTournamentRequest.getLocation())
                .matches(matches)
                .build();
    }
}
