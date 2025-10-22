package goralczyk.maciej.repository.tournament.memory;

import goralczyk.maciej.data.DataStore;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.repository.tournament.api.TournamentRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Simple in-memory repository for Tournament entity. Used in business layer.
 */
@RequestScoped
public class TournamentInMemoryRepository implements TournamentRepository {

    /**
     * Underlying data store. In future should be replaced with database connection.
     */
    private final DataStore store;

    /**
     * @param store data store
     */
    @Inject
    public TournamentInMemoryRepository(DataStore store) {
        this.store = store;
    }
    @Override
    public Optional<Tournament> find(UUID id) {
        return store.findAllTournaments().stream()
                .filter(tournament -> tournament.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Tournament> findAll() {
        return store.findAllTournaments();
    }

    @Override
    public void create(Tournament entity) {
        store.createTournament(entity);
    }

    @Override
    public void delete(Tournament entity) {
        throw new UnsupportedOperationException("Not implemented in in-memory tournament repository.");
    }

    @Override
    public void update(Tournament entity) {
        store.updateTournament(entity);
    }
}
