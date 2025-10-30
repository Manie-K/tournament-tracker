package goralczyk.maciej.repository.tournament.api;

import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.repository.api.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for Tournament entity. Used in business layer.
 */
public interface TournamentRepository extends Repository<Tournament, UUID> {
    /**
     * Finds single tournament by name.
     *
     * @param name tournament's name.
     * @return container with tournament.
     */
    Optional<Tournament> findByName(String name);
}
