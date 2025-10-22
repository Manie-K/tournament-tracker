package goralczyk.maciej.dto.match.function;

import goralczyk.maciej.dto.match.PatchMatchRequest;
import goralczyk.maciej.entity.Match;

import java.util.function.BiFunction;

public class UpdateMatchWithRequestFunction implements BiFunction<Match, PatchMatchRequest, Match> {
    @Override
    public Match apply(Match match, PatchMatchRequest patchMatchRequest) {
        return Match.builder()
                .id(match.getId())
                .startDateTime(patchMatchRequest.getStartDateTime())
                .result(patchMatchRequest.getResult())
                .tournament(match.getTournament())
                .participantA(match.getParticipantA())
                .participantB(match.getParticipantB())
                .build();
    }
}
