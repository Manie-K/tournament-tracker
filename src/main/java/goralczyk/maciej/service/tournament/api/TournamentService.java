package goralczyk.maciej.service.tournament.api;

import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TournamentService {
    /**
     * Finds single tournament.
     *
     * @param id tournament's id.
     * @return container with tournament.
     */
    Optional<Tournament> find(UUID id);

    /**
     * @return all available tournaments.
     */
    List<Tournament> findAll();

    /**
     * Creates new tournament.
     *
     * @param tournament tournament.
     */
    void create(Tournament tournament);

    /**
     * Updates existing tournament.
     *
     * @param tournament tournament to be updated
     */
    void update(Tournament tournament);

    /**
     * Deletes existing tournament.
     *
     * @param id existing tournament's id to be deleted
     */
    void delete(UUID id);
}
