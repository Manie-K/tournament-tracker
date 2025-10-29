package goralczyk.maciej.repository.match.memory;

import goralczyk.maciej.data.DataStore;
import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.repository.match.api.MatchRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Simple in-memory repository for Match entity. Used in business layer.
 */
@RequestScoped
public class MatchInMemoryRepository implements MatchRepository {

    /**
     * Underlying data store. In future should be replaced with database connection.
     */
    private final DataStore store;

    /**
     * @param store data store
     */
    @Inject
    public MatchInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Match> find(UUID id) {
        return store.findAllMatches().stream()
                .filter(match -> match.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Match> findAll() {
        return store.findAllMatches();
    }

    @Override
    public List<Match> findAllByUser(User user) {
        return store.findAllMatches().stream()
                .filter(match -> match.getParticipantA().equals(user) || match.getParticipantB().equals(user))
                .toList();
    }

    @Override
    public List<Match> findAllByTournament(Tournament tournament) {
        return store.findAllMatches().stream()
                .filter(match -> match.getTournament().equals(tournament))
                .toList();
    }

    @Override
    public void create(Match entity) {
        store.createMatch(entity);
    }

    @Override
    public void delete(Match entity) {
        store.deleteMatch(entity.getId());
    }

    @Override
    public void update(Match entity) {
        store.updateMatch(entity);
    }
}
