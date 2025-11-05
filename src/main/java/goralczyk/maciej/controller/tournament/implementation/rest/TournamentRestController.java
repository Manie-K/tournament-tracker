package goralczyk.maciej.controller.tournament.implementation.rest;

import goralczyk.maciej.controller.tournament.api.TournamentController;
import goralczyk.maciej.dto.DtoFunctionFactory;
import goralczyk.maciej.dto.tournament.GetTournamentResponse;
import goralczyk.maciej.dto.tournament.GetTournamentsResponse;
import goralczyk.maciej.dto.tournament.PatchTournamentRequest;
import goralczyk.maciej.dto.tournament.PutTournamentRequest;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.service.tournament.api.TournamentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;

import java.util.UUID;

/**
 * Simple framework agnostic implementation of controller.
 */
@Path("")//Annotation required by the specification.
public class TournamentRestController implements TournamentController
{
    /**
     * Tournament service.
     */
    private final TournamentService service;

    /**
     * Factory producing functions for conversion between DTO and entities.
     */
    private final DtoFunctionFactory factory;

    /**
     * @param service tournament service
     * @param factory factory producing functions for conversion between DTO and entities
     */
    @Inject
    public TournamentRestController(TournamentService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }


    @Override
    public GetTournamentsResponse getTournaments() {
        return factory.tournamentsToResponse().apply(service.findAll());
    }

    @Override
    public GetTournamentResponse getTournament(UUID uuid) {
        return service.find(uuid)
                .map(factory.tournamentToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putTournament(UUID id, PutTournamentRequest request) {
        service.create(factory.requestToTournament().apply(id, request));
    }

    @Override
    public void patchTournament(UUID id, PatchTournamentRequest request) {
        factory.updateTournament().apply(service.find(id).orElseThrow(NotFoundException::new), request);
    }

    @Override
    public void deleteTournament(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
