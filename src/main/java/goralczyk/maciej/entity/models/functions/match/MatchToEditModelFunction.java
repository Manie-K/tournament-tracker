package goralczyk.maciej.entity.models.functions.match;

import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.models.EditMatchModel;

import java.io.Serializable;
import java.util.function.Function;

public class MatchToEditModelFunction implements Function<Match, EditMatchModel> , Serializable {
    @Override
    public EditMatchModel apply(Match match) {
        return EditMatchModel.builder()
                .participantAName(match.getParticipantA().getName())
                .participantBName(match.getParticipantB().getName())
                .tournament(match.getTournament())
                .build();
    }
}
