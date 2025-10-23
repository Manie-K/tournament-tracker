package goralczyk.maciej.controller.tournament.implementation;

import goralczyk.maciej.controller.servlet.exception.NotFoundException;
import goralczyk.maciej.controller.tournament.api.TournamentController;
import goralczyk.maciej.dto.DtoFunctionFactory;
import goralczyk.maciej.dto.tournament.GetTournamentResponse;
import goralczyk.maciej.dto.tournament.GetTournamentsResponse;
import goralczyk.maciej.dto.tournament.PatchTournamentRequest;
import goralczyk.maciej.dto.tournament.PutTournamentRequest;
import goralczyk.maciej.service.tournament.api.TournamentService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@RequestScoped
public class TournamentControllerImplementation implements TournamentController {

    private final DtoFunctionFactory dtoFactory;
    private final TournamentService tournamentService;

    @Inject
    public TournamentControllerImplementation(DtoFunctionFactory dtoFactory, TournamentService tournamentService) {
        this.dtoFactory = dtoFactory;
        this.tournamentService = tournamentService;
    }

    @Override
    public GetTournamentsResponse getTournaments() {
        return dtoFactory.tournamentsToResponse().apply(tournamentService.findAll());
    }

    @Override
    public GetTournamentResponse getTournament(UUID uuid) {
        return tournamentService.find(uuid)
                .map(dtoFactory.tournamentToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putTournament(UUID id, PutTournamentRequest request) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void patchTournament(UUID id, PatchTournamentRequest request) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void deleteTournament(UUID id) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
