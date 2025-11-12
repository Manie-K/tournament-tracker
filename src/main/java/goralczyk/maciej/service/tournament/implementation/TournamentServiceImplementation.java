package goralczyk.maciej.service.tournament.implementation;

import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.repository.tournament.api.TournamentRepository;
import goralczyk.maciej.repository.user.api.UserRepository;
import goralczyk.maciej.service.tournament.api.TournamentService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for all business actions regarding tournament.
 */
@ApplicationScoped
@NoArgsConstructor(force = true)
public class TournamentServiceImplementation implements TournamentService {

    /**
     * Repository for tournament.
     */
    private final TournamentRepository tournamentRepository;

    /**
     * @param tournamentRepository repository for tournament entity
     */
    @Inject
    public TournamentServiceImplementation(TournamentRepository tournamentRepository)
    {
        this.tournamentRepository = tournamentRepository;
    }

    @Transactional
    @Override
    public Optional<Tournament> find(UUID id) {
        return tournamentRepository.find(id);
    }

    @Transactional
    @Override
    public Optional<Tournament> findByName(String name) {
        return tournamentRepository.findByName(name);
    }

    @Transactional
    @Override
    public List<Tournament> findAll() {
        return tournamentRepository.findAll();
    }

    @Transactional
    @Override
    public void create(Tournament tournament) {
        tournamentRepository.create(tournament);
    }

    @Transactional
    @Override
    public void update(Tournament tournament) {
        tournamentRepository.update(tournament);
    }

    @Transactional
    @Override
    public boolean delete(UUID id)
    {
        tournamentRepository.delete(tournamentRepository.find(id).orElseThrow());
        return true;
    }
}
