package goralczyk.maciej.repository.match.api;

import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.repository.api.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for Match entity. Used in business layer.
 */
public interface MatchRepository extends Repository<Match, UUID> {
    List<Match> findAllByUser(User user);
    List<Match> findAllByTournament(Tournament tournament);
    Optional<Match> findByIdAndUser(UUID id, User user);

}
