package goralczyk.maciej.dto;

import goralczyk.maciej.entity.models.functions.match.*;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.function.Function;

/**
 * Factor for creating {@link Function} implementation for converting between various objects used in different layers.
 * Instead of injecting multiple function objects single factory is injected.
 */
@ApplicationScoped

public class ModelFunctionFactory {
    public MatchToModelFunction matchToModel() {
        return new MatchToModelFunction();
    }

    public CreateMatchToModelFunction createMatchToModel() {
        return new CreateMatchToModelFunction();
    }

    public CreateMatchToEntityFunction createMatchToEntity() {
        return new CreateMatchToEntityFunction();
    }

    public EditMatchToEntityFunction editMatchToEntity() {
        return new EditMatchToEntityFunction();
    }

    public MatchToEditModelFunction entityToEditModel() {
        return new MatchToEditModelFunction();
    }
}
