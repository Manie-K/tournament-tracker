package goralczyk.maciej.entity.models.functions.match;

import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.entity.models.CreateMatchModel;
import goralczyk.maciej.service.user.api.UserService;
import jakarta.inject.Inject;

import java.io.Serializable;
import java.util.function.Function;

public class CreateMatchToEntityFunction implements Function<CreateMatchModel, Match>, Serializable
{
    private final UserService userService;

    public CreateMatchToEntityFunction(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Match apply(CreateMatchModel createMatch) {
        User userA = userService.findByName(createMatch.getParticipantAName()).orElse(null);
        User userB = userService.findByName(createMatch.getParticipantBName()).orElse(null);

        return Match.builder()
                .id(createMatch.getId())
                .participantA(userA)
                .participantB(userB)
                .tournament(createMatch.getTournament())
                .startDateTime(createMatch.getStartDateTime())
                .build();
    }
}
