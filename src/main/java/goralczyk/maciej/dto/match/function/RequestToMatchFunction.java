package goralczyk.maciej.dto.match.function;

import goralczyk.maciej.dto.match.PutMatchRequest;
import goralczyk.maciej.entity.Match;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToMatchFunction implements BiFunction<UUID, PutMatchRequest, Match> {
    @Override
    public Match apply(UUID uuid, PutMatchRequest putMatchRequest) {
        return Match.builder()
                .id(uuid)
                .participantA(putMatchRequest.getParticipantA())
                .participantB(putMatchRequest.getParticipantB())
                .tournament(putMatchRequest.getTournament())
                .startDateTime(putMatchRequest.getStartDateTime())
                .result(putMatchRequest.getResult())
                .build();
    }
}
