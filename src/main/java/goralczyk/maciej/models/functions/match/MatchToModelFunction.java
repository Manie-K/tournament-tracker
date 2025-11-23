package goralczyk.maciej.models.functions.match;

import goralczyk.maciej.entity.Match;
import goralczyk.maciej.models.match.MatchModel;

import java.io.Serializable;
import java.util.function.Function;

public class MatchToModelFunction implements Function<Match, MatchModel>, Serializable
{
    @Override
    public MatchModel apply(Match match) {
        return MatchModel.builder()
                .id(match.getId())
                .participantAName(match.getParticipantA().getName())
                .tournamentName(match.getTournament().getName())
                .startDateTime(match.getStartDateTime())
                .build();
    }
}
