package goralczyk.maciej.service.match;

import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.Role;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.repository.match.api.MatchRepository;
import goralczyk.maciej.repository.user.api.UserRepository;
import goralczyk.maciej.service.tournament.TournamentService;
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
    private final UserRepository userRepository;

    /**
     * Service for users.
     */
    private final TournamentService tournamentService;

    private final SecurityContext securityContext;


    @Inject
    public MatchService(MatchRepository matchRepository, UserRepository userRepository, TournamentService tournamentService, SecurityContext securityContext)
    {
        this.matchRepository = matchRepository;
        this.userRepository = userRepository;
        this.tournamentService = tournamentService;
        this.securityContext = securityContext;
    }

    @RolesAllowed(Role.ADMIN)
    public Optional<Match> find(UUID id) {
        return matchRepository.find(id);
    }

    @RolesAllowed(Role.ADMIN)
    public List<Match> findAll() {
        return matchRepository.findAll();
    }

    @RolesAllowed({Role.ADMIN, Role.USER})
    public List<Match> findAllByUser(UUID userId) {
        User user = userRepository.find(userId).orElseThrow(NotFoundException::new);
        return matchRepository.findAllByUser(user);
    }

    @RolesAllowed(Role.ADMIN)
    public List<Match> findAllByTournament(UUID tournamentId) {
        Tournament tournament = tournamentService.find(tournamentId).orElseThrow(NotFoundException::new);
        return matchRepository.findAllByTournament(tournament);
    }

    @RolesAllowed({Role.ADMIN, Role.USER})
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

    @RolesAllowed({Role.USER, Role.ADMIN})
    public void createByCaller(Match match)
    {
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName()).orElseThrow(NotFoundException::new);
        match.setParticipantA(user);
        create(match);
    }


    @RolesAllowed({Role.USER, Role.ADMIN})
    public void updateByCaller(Match match) {
        if(securityContext.isCallerInRole(Role.ADMIN)){
            update(match);
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName()).orElseThrow(NotFoundException::new);

        findByIdAndUser(match.getId(), user).orElseThrow(IllegalStateException::new);
        matchRepository.update(match);
    }

    @RolesAllowed({Role.USER, Role.ADMIN})
    public void update(Match match) {

        matchRepository.update(match);
    }

    @RolesAllowed({Role.USER, Role.ADMIN})
    public boolean delete(UUID id) {
        matchRepository.delete(matchRepository.find(id).orElseThrow(NotFoundException::new));
        return true;
    }

    @RolesAllowed({Role.USER, Role.ADMIN})
    public boolean deleteByCaller(UUID id) {
        if (securityContext.isCallerInRole(Role.ADMIN)) {
            delete(id);
        }

        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);

        findByIdAndUser(id, user).orElseThrow(IllegalStateException::new);
        return delete(id);
    }

    @RolesAllowed({Role.ADMIN, Role.USER})
    public List<Match> findAllByCaller()
    {
        if(securityContext.isCallerInRole(Role.ADMIN)){
            return findAll();
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName()).orElseThrow(NotFoundException::new);
        return findAllByUser(user.getId());
    }

    @RolesAllowed({Role.USER, Role.ADMIN})
    public Optional<Match> findByCaller(UUID id)
    {
        if(securityContext.isCallerInRole(Role.ADMIN))
        {
            return find(id);
        }
        String login = securityContext.getCallerPrincipal().getName();
        User user = userRepository.findByLogin(login).orElseThrow(NotFoundException::new);
        return findByIdAndUser(id, user);
    }


    private Optional<Match> findByIdAndUser(UUID id, User user)
    {
        return matchRepository.findByIdAndUser(id, user);
    }
}
