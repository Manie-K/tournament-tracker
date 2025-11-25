package goralczyk.maciej.dto;

import goralczyk.maciej.models.functions.match.*;
import goralczyk.maciej.models.functions.tournament.*;
import goralczyk.maciej.models.functions.user.UserToModelFunction;
import goralczyk.maciej.models.functions.user.UsersToModelFunction;
import goralczyk.maciej.repository.tournament.api.TournamentRepository;
import goralczyk.maciej.repository.user.api.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.function.Function;

/**
 * Factor for creating {@link Function} implementation for converting between various objects used in different layers.
 * Instead of injecting multiple function objects single factory is injected.
 */
@ApplicationScoped
public class ModelFunctionFactory {
    @Inject
    UserRepository userRepository;

    @Inject
    TournamentRepository tournamentRepository;

    public MatchToModelFunction matchToModel() {
        return new MatchToModelFunction();
    }

    public MatchesToModelFunction matchesToModel() {
        return new MatchesToModelFunction();
    }

    public CreateMatchToEntityFunction createMatchToEntity() {
        return new CreateMatchToEntityFunction(userRepository, tournamentRepository);
    }

    public EditMatchToEntityFunction editMatchToEntity() {
        return new EditMatchToEntityFunction();
    }

    public MatchToEditModelFunction entityToEditModel() {
        return new MatchToEditModelFunction();
    }


    public TournamentToModelFunction tournamentToModel() {
        return new TournamentToModelFunction();
    }
    public TournamentsToModelFunction tournamentsToModel() {
        return new TournamentsToModelFunction();
    }

    public TournamentToEditModelFunction tournamentToEditModel() {
        return new TournamentToEditModelFunction();
    }

    public EditTournamentToTournamentFunction editTournamentToTournament() {
        return new EditTournamentToTournamentFunction();
    }

    public CreateTournamentToEntityFunction createTournamentToEntity() {
        return new CreateTournamentToEntityFunction();
    }

    public UsersToModelFunction usersToModel() {
        return new UsersToModelFunction();
    }

    public UserToModelFunction userToModel() {return new UserToModelFunction();}
}
