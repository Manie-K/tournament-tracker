package goralczyk.maciej.dto;

import goralczyk.maciej.entity.models.functions.match.*;
import goralczyk.maciej.service.tournament.TournamentService;
import goralczyk.maciej.service.user.UserService;
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
    UserService userService;

    @Inject
    TournamentService tournamentService;

    public MatchToModelFunction matchToModel() {
        return new MatchToModelFunction();
    }

    public CreateMatchToModelFunction createMatchToModel() {
        return new CreateMatchToModelFunction();
    }

    public CreateMatchToEntityFunction createMatchToEntity() {
        return new CreateMatchToEntityFunction(userService);
    }

    public EditMatchToEntityFunction editMatchToEntity() {
        return new EditMatchToEntityFunction(tournamentService, userService);
    }

    public MatchToEditModelFunction entityToEditModel() {
        return new MatchToEditModelFunction();
    }
}
