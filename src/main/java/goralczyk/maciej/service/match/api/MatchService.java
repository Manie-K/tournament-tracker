package goralczyk.maciej.service.match.api;


import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MatchService {
    /**
     * Finds single match.
     *
     * @param id match's id.
     * @return container with match.
     */
    Optional<Match> find(UUID id);

    /**
     * @return all available matches.
     */
    List<Match> findAll();

    /**
     * @return all available matches of given user.*
     *
     * @param userId user's ID.
     */
    List<Match> findAllByUser(UUID userId);

    /**
     * @return all available matches of given tournament.
     *
     * @param tournamentId tournament's ID.
     */
    List<Match> findAllByTournament(UUID tournamentId);

    /**
     * Creates new match.
     *
     * @param match match.
     */
    void create(Match match);

    /**
     * Updates existing match.
     *
     * @param match match to be updated
     */
    void update(Match match);

    /**
     * Deletes existing match.
     *
     * @param id existing match's id to be deleted
     */
    boolean delete(UUID id);
}
