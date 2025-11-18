package goralczyk.maciej.dto.match.function;

import goralczyk.maciej.dto.match.PutMatchRequest;
import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.service.user.UserService;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToMatchFunction implements BiFunction<UUID, PutMatchRequest, Match>
{
    private final UserService userService;

    public RequestToMatchFunction(UserService userService) {
        this.userService = userService;
    }
    @Override
    public Match apply(UUID uuid, PutMatchRequest putMatchRequest) {
        System.out.println("[DTO] Converting PutMatchRequest to Match entity. PutMatchRequest: " + putMatchRequest);
        User participantA = userService.find(putMatchRequest.getParticipantAId()).orElseThrow(BadRequestException::new);
        System.out.println("[DTO] ParticipantA: " + participantA);
        return Match.builder()
                .id(uuid)
                .participantA(participantA)
                .participantB(null)
                .tournament(putMatchRequest.getTournament())
                .startDateTime(putMatchRequest.getStartDateTime())
                .result(putMatchRequest.getResult())
                .build();
    }
}
