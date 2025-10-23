package goralczyk.maciej.controller.match.api;

import goralczyk.maciej.dto.match.GetMatchResponse;
import goralczyk.maciej.dto.match.GetMatchesResponse;
import goralczyk.maciej.dto.match.PatchMatchRequest;
import goralczyk.maciej.dto.match.PutMatchRequest;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.entity.User;

import java.util.UUID;

/**
 * Controller for managing tournaments.
 */

public interface MatchController
{
    /**
     * @return all matches representations.
     */
    GetMatchesResponse getMatches();

    /**
     * @return all matches of user representations.
     */
    GetMatchesResponse getUserMatches(User user);

    /**
     * @return all matches of tournament representations.
     */
    GetMatchesResponse getTournamentMatches(Tournament tournament);


    /**
     * @param uuid match's id.
     * @return match representation.
     */
    GetMatchResponse getMatch(UUID uuid);

    /**
     * @param id      match's id.
     * @param request new match representation.
     */
    void putMatch(UUID id, PutMatchRequest request);

    /**
     * @param id      match's id.
     * @param request match update representation.
     */
    void patchMatch(UUID id, PatchMatchRequest request);

    /**
     * @param id match's id.
     */
    void deleteMatch(UUID id);
}