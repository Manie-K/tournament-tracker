package goralczyk.maciej.entity.models.functions.match;

import goralczyk.maciej.entity.models.CreateMatchModel;
import goralczyk.maciej.entity.models.MatchModel;

import java.io.Serializable;
import java.util.function.Function;

public class CreateMatchToModelFunction implements Function<CreateMatchModel, MatchModel>, Serializable
{
    @Override
    public MatchModel apply(CreateMatchModel createMatch) {
        return MatchModel.builder()
                .id(createMatch.getId())
                .participantAName(createMatch.getParticipantAName())
                .participantBName(createMatch.getParticipantBName())
                .tournament(createMatch.getTournament())
                .startDateTime(createMatch.getStartDateTime())
                .build();
    }
}
