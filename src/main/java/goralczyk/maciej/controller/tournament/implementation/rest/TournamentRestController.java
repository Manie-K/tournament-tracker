package goralczyk.maciej.controller.tournament.implementation.rest;

import goralczyk.maciej.controller.tournament.api.TournamentController;
import goralczyk.maciej.dto.DtoFunctionFactory;
import goralczyk.maciej.dto.tournament.GetTournamentResponse;
import goralczyk.maciej.dto.tournament.GetTournamentsResponse;
import goralczyk.maciej.dto.tournament.PatchTournamentRequest;
import goralczyk.maciej.dto.tournament.PutTournamentRequest;
import goralczyk.maciej.entity.Role;
import goralczyk.maciej.service.tournament.TournamentService;
import jakarta.annotation.security.RolesAllowed;
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
@Log
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
     * @param service tournament service
     * @param factory factory producing functions for conversion between DTO and entities
     */
    @Inject
    public TournamentRestController(TournamentService service, DtoFunctionFactory factory, UriInfo uriInfo) {
        this.service = service;
        this.factory = factory;
        this.uriInfo = uriInfo;
    }


    @Override
    @RolesAllowed({Role.ADMIN, Role.USER})
    public GetTournamentsResponse getTournaments() {
        return factory.tournamentsToResponse().apply(service.findAll());
    }

    @Override
    @RolesAllowed({Role.ADMIN, Role.USER})
    public GetTournamentResponse getTournament(UUID uuid) {
        return service.find(uuid)
                .map(factory.tournamentToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    @RolesAllowed(Role.ADMIN)
    public void putTournament(UUID id, PutTournamentRequest request)
    {
        service.create(factory.requestToTournament().apply(id, request));
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader("Location", uriInfo.getAbsolutePath().toString() + "/" + id);
    }

    @Override
    @RolesAllowed(Role.ADMIN)
    public void patchTournament(UUID id, PatchTournamentRequest request) {
        service.update(factory.updateTournament().apply(service.find(id).orElseThrow(NotFoundException::new), request));
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    @RolesAllowed(Role.ADMIN)
    public void deleteTournament(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
