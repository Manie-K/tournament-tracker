package goralczyk.maciej.models.functions.match;

import goralczyk.maciej.entity.Match;
import goralczyk.maciej.models.match.EditMatchModel;

import java.io.Serializable;
import java.util.function.BiFunction;

public class EditMatchToEntityFunction implements BiFunction<Match, EditMatchModel, Match>, Serializable
{
    @Override
    public Match apply(Match entity, EditMatchModel editMatch) {
        return Match.builder()
                .id(entity.getId())
                .participantA(entity.getParticipantA())
                .participantB(entity.getParticipantB())
                .tournament(entity.getTournament())
                .startDateTime(editMatch.getDate())
                .build();
    }
}
