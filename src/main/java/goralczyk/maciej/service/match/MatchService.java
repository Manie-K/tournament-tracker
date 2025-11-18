package goralczyk.maciej.service.match;

import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.repository.match.api.MatchRepository;
import goralczyk.maciej.service.tournament.TournamentService;
import goralczyk.maciej.service.user.api.UserService;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * Service layer for all business actions regarding match.
 */
@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class MatchService
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
    public MatchService(MatchRepository matchRepository, UserService userService, TournamentService tournamentService)
    {
        this.matchRepository = matchRepository;
        this.userService = userService;
        this.tournamentService = tournamentService;
    }

    public Optional<Match> find(UUID id) {
        return matchRepository.find(id);
    }

    public List<Match> findAll() {
        return matchRepository.findAll();
    }

    public List<Match> findAllByUser(UUID userId) {
        User user = userService.find(userId).orElseThrow(NotFoundException::new);
        return matchRepository.findAllByUser(user);
    }

    public List<Match> findAllByTournament(UUID tournamentId) {
        Tournament tournament = tournamentService.find(tournamentId).orElseThrow(NotFoundException::new);
        return matchRepository.findAllByTournament(tournament);
    }

    public void create(Match match) {
        if(matchRepository.find(match.getId()).isPresent())
        {
            throw new IllegalStateException("Match with this ID already exists");
        } else if (tournamentService.find(match.getTournament().getId()).isEmpty())
        {
            throw new IllegalStateException("Tournament with this ID doesn't exist");
        }

        System.out.println("[Service] Create: " + match);
        matchRepository.create(match);
    }

    public void update(Match match) {
        matchRepository.update(match);
    }

    public boolean delete(UUID id) {
        matchRepository.delete(matchRepository.find(id).orElseThrow());
        return true;
    }
}
