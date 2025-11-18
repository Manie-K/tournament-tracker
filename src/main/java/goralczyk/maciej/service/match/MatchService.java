package goralczyk.maciej.service.match;

import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.Role;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.repository.match.api.MatchRepository;
import goralczyk.maciej.service.tournament.TournamentService;
import goralczyk.maciej.service.user.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
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
@NoArgsConstructor(force = true, access = lombok.AccessLevel.PUBLIC)
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

    private final SecurityContext securityContext;


    @Inject
    public MatchService(MatchRepository matchRepository, UserService userService, TournamentService tournamentService, SecurityContext securityContext)
    {
        this.matchRepository = matchRepository;
        this.userService = userService;
        this.tournamentService = tournamentService;
        this.securityContext = securityContext;
    }

    @RolesAllowed(Role.USER)
    public Optional<Match> find(UUID id) {
        return matchRepository.find(id);
    }

    @RolesAllowed(Role.USER)
    public List<Match> findAll() {
        return matchRepository.findAll();
    }

    @RolesAllowed(Role.USER)
    public List<Match> findAllByUser(UUID userId) {
        User user = userService.find(userId).orElseThrow(NotFoundException::new);
        return matchRepository.findAllByUser(user);
    }

    @RolesAllowed(Role.USER)
    public List<Match> findAllByTournament(UUID tournamentId) {
        Tournament tournament = tournamentService.find(tournamentId).orElseThrow(NotFoundException::new);
        return matchRepository.findAllByTournament(tournament);
    }

    @RolesAllowed(Role.ADMIN)
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

    @RolesAllowed(Role.USER)
    public void update(Match match) {
        matchRepository.update(match);
    }

    @RolesAllowed(Role.USER)
    public boolean delete(UUID id) {
        matchRepository.delete(matchRepository.find(id).orElseThrow());
        return true;
    }
}
