package goralczyk.maciej.controller.match.api;

import goralczyk.maciej.dto.match.GetMatchResponse;
import goralczyk.maciej.dto.match.GetMatchesResponse;
import goralczyk.maciej.dto.match.PatchMatchRequest;
import goralczyk.maciej.dto.match.PutMatchRequest;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.entity.User;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

/**
 * Controller for managing tournaments.
 */

public interface MatchController
{
    /**
     * @return all matches representations.
     */
    @GET
    @Path("/matches")
    @Produces(MediaType.APPLICATION_JSON)
    GetMatchesResponse getMatches();

    /**
     * @return all matches of user representations.
     */
    @GET
    @Path("/users/{id}/matches")
    @Produces(MediaType.APPLICATION_JSON)
    GetMatchesResponse getUserMatches(@PathParam("id") UUID id);

    /**
     * @return all matches of tournament representations.
     */
    @GET
    @Path("/tournaments/{id}/matches")
    @Produces(MediaType.APPLICATION_JSON)
    GetMatchesResponse getTournamentMatches(@PathParam("id") UUID id);


    /**
     * @param id match's id.
     * @return match representation.
     */
    @GET
    @Path("/matches/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetMatchResponse getMatch(@PathParam("id") UUID id);

    /**
     * @param id      match's id.
     * @param request new match representation.
     */
    @PUT
    @Path("/tournaments/{tournamentId}/matches/{id}")
    void putMatch(@PathParam("tournamentId") UUID tournamentId, @PathParam("id") UUID id, PutMatchRequest request);

    /**
     * @param id      match's id.
     * @param request match update representation.
     */
    @PATCH
    @Path("/tournaments/{tournamentId}/matches/{id}")
    void patchMatch(@PathParam("tournamentId") UUID tournamentId, @PathParam("id") UUID id, PatchMatchRequest request);

    /**
     * @param id match's id.
     */
    @DELETE
    @Path("/tournaments/{tournamentId}/matches/{id}")
    void deleteMatch(@PathParam("tournamentId") UUID tournamentId, @PathParam("id") UUID id);
}