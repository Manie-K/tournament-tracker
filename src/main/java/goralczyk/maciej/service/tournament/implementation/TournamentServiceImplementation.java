package goralczyk.maciej.service.tournament.implementation;

import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.repository.tournament.api.TournamentRepository;
import goralczyk.maciej.repository.user.api.UserRepository;
import goralczyk.maciej.service.tournament.api.TournamentService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
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
    @Override
    public Optional<Tournament> find(UUID id) {
        return tournamentRepository.find(id);
    }

    @Override
    public List<Tournament> findAll() {
        return tournamentRepository.findAll();
    }

    @Override
    public void create(Tournament tournament) {
        tournamentRepository.create(tournament);
    }

    @Override
    public void update(Tournament tournament) {
        tournamentRepository.update(tournament);
    }

    @Override
    public void delete(UUID id) {
        tournamentRepository.delete(tournamentRepository.find(id).orElseThrow());
    }
}
