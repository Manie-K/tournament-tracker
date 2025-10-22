package goralczyk.maciej.configuration.listener;

import goralczyk.maciej.configuration.StringConstants;
import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.Role;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.service.match.api.MatchService;
import goralczyk.maciej.service.tournament.api.TournamentService;
import goralczyk.maciej.service.user.api.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Listener started automatically on servlet context initialized. Fetches instance of the datasource from the servlet
 * context and fills it with default content. Normally this class would fetch database datasource and init data only in
 * cases of empty database. When using persistence storage application instance should be initialized only during first
 * run in order to init database with starting data. Good place to create first default admin user.
 */

@ApplicationScoped
public class InitializedData
{
    /**
     * User service.
     */
    private UserService userService;

    /**
     * Match service.
     */
    private MatchService matchService;

    /**
     * Tournament service.
     */
    private TournamentService tournamentService;

    private RequestContextController requestContextController;

    @Inject
    public InitializedData(RequestContextController requestContextController, UserService userService, MatchService matchService, TournamentService tournamentService)
    {
        this.requestContextController = requestContextController;
        this.userService = userService;
        this.matchService = matchService;
        this.tournamentService = tournamentService;
    }

    public void onStart(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    /**
     * Initializes database with some example values. Should be called after creating this object. This object should be
     * created only once.
     */
    @SneakyThrows
    private void init()
    {
        requestContextController.activate();

        User admin = User.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .name("Admin")
                .dateOfBirth(LocalDate.EPOCH)
                .role(Role.Admin)
                .matches(List.of())
                .photo(null)
                .build();

        User user = User.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                .name("User")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .role(Role.Normal)
                .matches(List.of())
                .photo(null)
                .build();

        User me = User.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000003"))
                .name("Maciej")
                .dateOfBirth(LocalDate.of(2003, 11, 20))
                .role(Role.Normal)
                .matches(List.of())
                .photo(null)
                .build();

        User lewandowski = User.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000004"))
                .name("Robert Lewandowski")
                .dateOfBirth(LocalDate.of(1988, 8, 21))
                .role(Role.Normal)
                .matches(List.of())
                .photo(null)
                .build();

        userService.create(admin);
        userService.create(user);
        userService.create(me);
        userService.create(lewandowski);

        Tournament championsLeague = Tournament.builder()
                .id(UUID.fromString("10000000-0000-0000-0000-000000000001"))
                .name("Champions League")
                .location("Wembley, London")
                .build();

        Tournament euro = Tournament.builder()
                .id(UUID.fromString("10000000-0000-0000-0000-000000000002"))
                .name("Euro")
                .location("Europe")
                .build();

        tournamentService.create(championsLeague);
        tournamentService.create(euro);

        Match finalMatch = Match.builder()
                .id(UUID.fromString("20000000-0000-0000-0000-000000000001"))
                .startDateTime(LocalDateTime.now())
                .participantA(lewandowski)
                .participantB(me)
                .tournament(championsLeague)
                .result(1)
                .build();

        matchService.create(finalMatch);

        requestContextController.deactivate();
    }

    /**
     * @param name name of the desired resource
     * @return array of bytes read from the resource
     */
    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            if (is != null) {
                return is.readAllBytes();
            } else {
                throw new IllegalStateException("Unable to get resource %s".formatted(name));
            }
        }
    }

}
