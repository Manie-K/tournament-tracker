package goralczyk.maciej.service.match.implementation;

import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.repository.match.api.MatchRepository;
import goralczyk.maciej.service.match.api.MatchService;
import goralczyk.maciej.service.tournament.api.TournamentService;
import goralczyk.maciej.service.user.api.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
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
     * Repository for matches.
     */
    private final MatchRepository matchRepository;

    /**
     * Service for users.
     */
    private final UserService userService;

    /**
     * Service for users.
     */
    private final TournamentService tournamentService;

    /**
     * @param matchRepository repository for match entity
     */
    @Inject
    public MatchServiceImplementation(MatchRepository matchRepository, UserService userService, TournamentService tournamentService)
    {
        this.matchRepository = matchRepository;
        this.userService = userService;
        this.tournamentService = tournamentService;
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
    public List<Match> findAllByUser(UUID userId) {
        User user = userService.find(userId).orElseThrow(NotFoundException::new);
        return matchRepository.findAll().stream().filter(match -> match.getParticipantA().equals(user) || match.getParticipantB().equals(user)).collect(Collectors.toList());
    }

    @Override
    public List<Match> findAllByTournament(UUID tournamentId) {
        Tournament tournament = tournamentService.find(tournamentId).orElseThrow(NotFoundException::new);
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
    public boolean delete(UUID id) {
        Optional<Match> match = matchRepository.find(id);
        if (match.isEmpty()) {
            return false;
        }
        matchRepository.delete(match.get());
        return true;
    }
}
