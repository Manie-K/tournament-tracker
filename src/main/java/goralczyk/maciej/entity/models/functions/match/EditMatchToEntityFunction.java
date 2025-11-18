package goralczyk.maciej.entity.models.functions.match;

import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.entity.models.EditMatchModel;
import goralczyk.maciej.service.tournament.TournamentService;
import goralczyk.maciej.service.user.api.UserService;
import jakarta.inject.Inject;

import java.io.Serializable;
import java.util.function.BiFunction;

public class EditMatchToEntityFunction implements BiFunction<Match, EditMatchModel, Match>, Serializable
{

    private final TournamentService tournamentService;
    private final UserService userService;

    public EditMatchToEntityFunction(TournamentService tournamentService, UserService userService) {
        this.tournamentService = tournamentService;
        this.userService = userService;
    }

    @Override
    public Match apply(Match entity, EditMatchModel editMatch) {
        User userA = userService.findByName(editMatch.getParticipantAName()).orElse(null);
        User userB = userService.findByName(editMatch.getParticipantBName()).orElse(null);

        Tournament tournament = tournamentService.findByName(editMatch.getTournament().getName()).orElse(null);

        return Match.builder()
                .id(entity.getId())
                .participantA(userA)
                .participantB(userB)
                .tournament(tournament)
                .startDateTime(entity.getStartDateTime())
                .build();
    }
}
