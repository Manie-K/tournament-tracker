package goralczyk.maciej.service.tournament;

import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.repository.tournament.api.TournamentRepository;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for all business actions regarding tournament.
 */
@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class TournamentService
{
    /**
     * Repository for tournament.
     */
    private final TournamentRepository tournamentRepository;

    /**
     * @param tournamentRepository repository for tournament entity
     */
    @Inject
    public TournamentService(TournamentRepository tournamentRepository)
    {
        this.tournamentRepository = tournamentRepository;
    }


    public Optional<Tournament> find(UUID id) {
        return tournamentRepository.find(id);
    }

    public Optional<Tournament> findByName(String name) {
        return tournamentRepository.findByName(name);
    }

    public List<Tournament> findAll() {
        return tournamentRepository.findAll();
    }

    public void create(Tournament tournament) {
        if(tournamentRepository.find(tournament.getId()).isPresent())
        {
            throw new IllegalStateException("Tournament with this ID already exists");
        }

        tournamentRepository.create(tournament);
    }

    public void update(Tournament tournament) {
        tournamentRepository.update(tournament);
    }

    public boolean delete(UUID id)
    {
        tournamentRepository.delete(tournamentRepository.find(id).orElseThrow());
        return true;
    }
}
