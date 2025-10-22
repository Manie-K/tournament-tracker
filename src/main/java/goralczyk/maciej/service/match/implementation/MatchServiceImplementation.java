package goralczyk.maciej.service.match.implementation;

import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.repository.match.api.MatchRepository;
import goralczyk.maciej.repository.tournament.api.TournamentRepository;
import goralczyk.maciej.repository.user.api.UserRepository;
import goralczyk.maciej.service.match.api.MatchService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * Service layer for all business actions regarding match.
 */
@ApplicationScoped
@NoArgsConstructor(force = true)
public class MatchServiceImplementation implements MatchService
{
    /**
     * Repository for match.
     */
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;
    private final TournamentRepository tournamentRepository;

    /**
     * @param matchRepository repository for match entity
     * @param userRepository repository for user entity
     * @param tournamentRepository repository for tournament entity
     */
    @Inject
    public MatchServiceImplementation(MatchRepository matchRepository, UserRepository userRepository, TournamentRepository tournamentRepository)
    {
        this.matchRepository = matchRepository;
        this.userRepository = userRepository;
        this.tournamentRepository = tournamentRepository;
    }

    @Override
    public Optional<Match> find(UUID id) {
        return matchRepository.find(id);
    }

    @Override
    public List<Match> findAll() {
        return matchRepository.findAll();
    }

    @Override
    public List<Match> findAll(User user) {
        return matchRepository.findAll().stream().filter(match -> match.getParticipantA().equals(user) || match.getParticipantB().equals(user)).collect(Collectors.toList());
    }

    @Override
    public List<Match> findAll(Tournament tournament) {
        return matchRepository.findAll().stream().filter(match -> match.getTournament().equals(tournament)).collect(Collectors.toList());
    }

    @Override
    public void create(Match match) {
        matchRepository.create(match);
    }

    @Override
    public void update(Match match) {
        matchRepository.update(match);
    }

    @Override
    public void delete(UUID id) {
        matchRepository.delete(matchRepository.find(id).orElseThrow());
    }
}
