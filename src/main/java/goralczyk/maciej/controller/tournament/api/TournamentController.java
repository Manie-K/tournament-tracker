package goralczyk.maciej.controller.tournament.api;

import goralczyk.maciej.dto.tournament.GetTournamentResponse;
import goralczyk.maciej.dto.tournament.GetTournamentsResponse;
import goralczyk.maciej.dto.tournament.PatchTournamentRequest;
import goralczyk.maciej.dto.tournament.PutTournamentRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

/**
 * Controller for managing tournaments.
 */
@Path("")
public interface TournamentController
{
    /**
     * @return all tournaments representations.
     */
    @GET
    @Path("/tournaments")
    @Produces(MediaType.APPLICATION_JSON)
    GetTournamentsResponse getTournaments();


    /**
     * @param uuid tournament's id.
     * @return tournaments representation.
     */
    @GET
    @Path("/tournaments/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetTournamentResponse getTournament(@PathParam("id") UUID uuid);

    /**
     * @param id      tournament's id.
     * @param request new tournament representation.
     */
    @PUT
    @Path("/tournaments/{id}")
    void putTournament(@PathParam("id") UUID id, PutTournamentRequest request);

    /**
     * @param id      tournament's id.
     * @param request tournament update representation.
     */
    @PATCH
    @Path("/tournaments/{id}")
    void patchTournament(@PathParam("id")  UUID id, PatchTournamentRequest request);

    /**
     * @param id tournament's id.
     */
    @DELETE
    @Path("/tournaments/{id}")
    void deleteTournament(@PathParam("id") UUID id);
}
