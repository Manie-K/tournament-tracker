package goralczyk.maciej.controller.match.implementation.rest;

import goralczyk.maciej.controller.match.api.MatchController;
import goralczyk.maciej.dto.DtoFunctionFactory;
import goralczyk.maciej.dto.match.GetMatchResponse;
import goralczyk.maciej.dto.match.GetMatchesResponse;
import goralczyk.maciej.dto.match.PatchMatchRequest;
import goralczyk.maciej.dto.match.PutMatchRequest;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.service.match.api.MatchService;
import goralczyk.maciej.service.tournament.api.TournamentService;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.util.UUID;

/**
 * Simple framework agnostic implementation of controller.
 */
@Path("")//Annotation required by the specification.

public class MatchRestController implements MatchController
{
    /**
     * Match service.
     */
    private final MatchService matchService;

    /**
     * Tournament service.
     */
    private final TournamentService tournamentService;

    /**
     * Factory producing functions for conversion between DTO and entities.
     */
    private final DtoFunctionFactory factory;

    /**
     * Allows to create {@link UriBuilder} based on current request.
     */
    private final UriInfo uriInfo;

    /**
     * Current HTTP Servlet response.
     */
    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        //ATM in this implementation only HttpServletRequest can be injected with CDI so JAX-RS injection is used.
        this.response = response;
    }

    /**
     * @param matchService match service
     * @param factory factory producing functions for conversion between DTO and entities
     * @param uriInfo allows to create {@link UriBuilder} based on current request
     */
    @Inject
    public MatchRestController(
            MatchService matchService,
            TournamentService tournamentService,
            DtoFunctionFactory factory,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo
    ) {
        this.matchService = matchService;
        this.tournamentService = tournamentService;
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @Override
    public GetMatchesResponse getMatches() {
        return factory.matchesToResponse().apply(matchService.findAll());
    }

    @Override
    public GetMatchesResponse getUserMatches(UUID userId) {
        return factory.matchesToResponse().apply(matchService.findAllByUser(userId));
    }

    @Override
    public GetMatchesResponse getTournamentMatches(UUID tournamentId) {
        return factory.matchesToResponse().apply(matchService.findAllByTournament(tournamentId));
    }

    @Override
    public GetMatchResponse getMatch(UUID id) {
        //change
        return factory.matchToResponse().apply(matchService.find(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public void putMatch(UUID tournamentId, UUID id, PutMatchRequest request) {
        //change
        Tournament tournament = tournamentService.find(tournamentId).orElseThrow(NotFoundException::new);
        request.setTournament(tournament);
        matchService.create(factory.requestToMatch().apply(id, request));

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader("Location", uriInfo.getAbsolutePath().toString() + "/" + id);
    }

    @Override
    public void patchMatch(UUID tournamentId, UUID id, PatchMatchRequest request) {
        if(matchService.findAllByTournament(tournamentId).isEmpty()) {
            throw new NotFoundException();
        }
        factory.updateMatch().apply(matchService.find(id).orElseThrow(NotFoundException::new), request);

    }

    @Override
    public void deleteMatch(UUID id) {
        matchService.find(id).ifPresentOrElse(
                entity -> matchService.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
