package goralczyk.maciej.models.functions.match;

import goralczyk.maciej.entity.Match;
import goralczyk.maciej.models.match.EditMatchModel;

import java.io.Serializable;
import java.util.function.Function;

public class MatchToEditModelFunction implements Function<Match, EditMatchModel> , Serializable {
    @Override
    public EditMatchModel apply(Match match) {
        return EditMatchModel.builder()
                .date(match.getStartDateTime())
                .version(match.getVersion())
                .build();
    }
}
