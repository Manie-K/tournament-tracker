package goralczyk.maciej.entity.models.functions.match;

import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.models.MatchModel;

import java.io.Serializable;
import java.util.function.Function;

public class MatchToModelFunction implements Function<Match, MatchModel>, Serializable
{
    @Override
    public MatchModel apply(Match match) {
        return MatchModel.builder()
                .id(match.getId())
                .participantAName(match.getParticipantA().getName())
                .participantBName(match.getParticipantB().getName())
                .tournament(match.getTournament())
                .startDateTime(match.getStartDateTime())
                .build();
    }
}
