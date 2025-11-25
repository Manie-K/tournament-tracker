package goralczyk.maciej.controller.match.implementation.rest;

import goralczyk.maciej.controller.match.api.MatchController;
import goralczyk.maciej.dto.DtoFunctionFactory;
import goralczyk.maciej.dto.match.GetMatchResponse;
import goralczyk.maciej.dto.match.GetMatchesResponse;
import goralczyk.maciej.dto.match.PatchMatchRequest;
import goralczyk.maciej.dto.match.PutMatchRequest;
import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.Role;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.service.match.MatchService;
import goralczyk.maciej.service.tournament.TournamentRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;

import java.util.UUID;

/**
 * Simple framework agnostic implementation of controller.
 */
@Path("")//Annotation required by the specification.
@RolesAllowed({Role.USER, Role.ADMIN})
@Log
public class MatchRestController implements MatchController
{
    /**
     * Match service.
     */
    private MatchService matchService;

    /**
     * Tournament service.
     */
    private TournamentRepository tournamentRepository;

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
     * @param factory factory producing functions for conversion between DTO and entities
     * @param uriInfo allows to create {@link UriBuilder} based on current request
     */
    @Inject
    public MatchRestController(
            DtoFunctionFactory factory,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo
    ) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    private void setMatchService(MatchService matchService) {
        this.matchService = matchService;
    }

    @EJB
    private void setTournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    @Override
    public GetMatchesResponse getMatches() {
        return factory.matchesToResponse().apply(matchService.findAllByCaller());
    }

    @Override
    public GetMatchesResponse getUserMatches(UUID userId) {
        return factory.matchesToResponse().apply(matchService.findAllByUser(userId));
    }

    @Override
    @RolesAllowed(Role.ADMIN)
    public GetMatchesResponse getTournamentMatches(UUID tournamentId) {
        return factory.matchesToResponse().apply(matchService.findAllByTournament(tournamentId));
    }

    @Override
    public GetMatchResponse getMatch(UUID id) {
        return factory.matchToResponse().apply(matchService.find(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public void putMatch(UUID tournamentId, UUID id, PutMatchRequest request) {
        Tournament tournament = tournamentRepository.find(tournamentId).orElseThrow(NotFoundException::new);
        request.setTournament(tournament);
        matchService.createByCaller(factory.requestToMatch().apply(id, request));

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader("Location", uriInfo.getAbsolutePath().toString());
    }

    @Override
    public void patchMatch(UUID tournamentId, UUID id, PatchMatchRequest request) {
        if(matchService.findAllByTournament(tournamentId).isEmpty()) {
            throw new NotFoundException();
        }
        Match match = factory.updateMatch().apply(matchService.findByCaller(id).orElseThrow(NotFoundException::new), request);
        matchService.updateByCaller(match);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    public void deleteMatch(UUID tournamentId, UUID id) {
        Match match = matchService.findByCaller(id).orElseThrow(NotFoundException::new);
        if(match.getTournament().getId().equals(tournamentId)) {
            System.out.println(match.getTournament().getId());
            matchService.deleteByCaller(id);
        }else{
            throw new NotFoundException();
        }
    }

}
