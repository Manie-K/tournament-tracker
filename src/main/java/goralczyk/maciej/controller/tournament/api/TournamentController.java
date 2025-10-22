package goralczyk.maciej.controller.tournament.api;

import goralczyk.maciej.dto.tournament.GetTournamentResponse;
import goralczyk.maciej.dto.tournament.GetTournamentsResponse;
import goralczyk.maciej.dto.tournament.PutTournamentRequest;
import goralczyk.maciej.dto.user.GetUserResponse;
import goralczyk.maciej.dto.user.GetUsersResponse;
import goralczyk.maciej.dto.user.PatchUserRequest;
import goralczyk.maciej.dto.user.PutUserRequest;

import java.util.UUID;

/**
 * Controller for managing tournaments.
 */

public interface TournamentController
{
    /**
     * @return all tournaments representations.
     */
    GetTournamentsResponse getTournaments();


    /**
     * @param uuid tournament's id.
     * @return tournaments representation.
     */
    GetTournamentResponse getTournament(UUID uuid);

    /**
     * @param id      tournament's id.
     * @param request new tournament representation.
     */
    void putTournament(UUID id, PutTournamentRequest request);

    /**
     * @param id      tournament's id.
     * @param request tournament update representation.
     */
    void patchTournament(UUID id, PatchTournamentRequest request);

    /**
     * @param id tournament's id.
     */
    void deleteTournament(UUID id);
}
