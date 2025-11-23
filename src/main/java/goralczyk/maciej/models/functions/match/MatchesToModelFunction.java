package goralczyk.maciej.models.functions.match;

import goralczyk.maciej.entity.Match;
import goralczyk.maciej.models.match.MatchesModel;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

public class MatchesToModelFunction implements Function<List<Match>, MatchesModel>, Serializable {

    @Override
    public MatchesModel apply(List<Match> matches) {
        return MatchesModel.builder()
                .matches(matches
                        .stream()
                        .map(m ->
                            MatchesModel.Match.builder()
                                .id(m.getId())
                                .participantAName(m.getParticipantA().getName())
                                .tournamentName(m.getTournament().getName())
                                .build()
                        )
                        .toList()
                )
                .build();
    }
}
