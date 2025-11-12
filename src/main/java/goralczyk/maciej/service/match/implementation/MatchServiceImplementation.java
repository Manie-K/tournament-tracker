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
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    @Transactional
    @Override
    public Optional<Match> find(UUID id) {
        return matchRepository.find(id);
    }

    @Transactional
    @Override
    public List<Match> findAll() {
        return matchRepository.findAll();
    }

    @Transactional
    @Override
    public List<Match> findAllByUser(UUID userId) {
        User user = userService.find(userId).orElseThrow(NotFoundException::new);
        return matchRepository.findAllByUser(user);
    }

    @Transactional
    @Override
    public List<Match> findAllByTournament(UUID tournamentId) {
        Tournament tournament = tournamentService.find(tournamentId).orElseThrow(NotFoundException::new);
        return matchRepository.findAllByTournament(tournament);
    }

    @Transactional
    @Override
    public void create(Match match) {
        matchRepository.create(match);
    }

    @Transactional
    @Override
    public void update(Match match) {
        matchRepository.update(match);
    }

    @Transactional
    @Override
    public boolean delete(UUID id) {
        /*Optional<Match> match = matchRepository.find(id);
        if (match.isEmpty()) {
            return false;
        }

        List<Match> tournamentMatches = tournamentService.find(match.get().getTournament().getId()).get().getMatches();
        List<Match> newMatches = new ArrayList<Match>();

        tournamentMatches.forEach(match1 -> {
            if(match1.getId().equals(id))
            {

            }
            else{
                newMatches.add(match1);
            }
        });
        System.out.println("new matches: " + newMatches);
        Tournament t = tournamentService.find(match.get().getTournament().getId()).get();
        t.setMatches(newMatches);
        tournamentService.update(t);
        System.out.println("Deleted match from tournament. Now it has: " + tournamentService.find(t.getId()).get().getMatches());
        matchRepository.delete(match.get());
        return true;*/
        matchRepository.delete(matchRepository.find(id).orElseThrow());
        return true;
    }
}
