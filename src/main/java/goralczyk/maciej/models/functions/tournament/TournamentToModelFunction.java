package goralczyk.maciej.models.functions.tournament;

import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.models.match.MatchModel;
import goralczyk.maciej.models.tournament.TournamentModel;

import java.io.Serializable;
import java.util.function.Function;

public class TournamentToModelFunction implements Function<Tournament, TournamentModel>, Serializable
{
    @Override
    public TournamentModel apply(Tournament tournament) {
        return TournamentModel.builder()
                .id(tournament.getId())
                .name(tournament.getName())
                .location(tournament.getLocation())
                .matches(tournament.getMatches().stream().map(match -> MatchModel.builder()
                                .id(match.getId())
                                .participantAName(match.getParticipantA().getName())
                                .tournamentName(tournament.getName())
                                .startDateTime(match.getStartDateTime())
                            .build())
                        .toList())
                .build();
    }
}
