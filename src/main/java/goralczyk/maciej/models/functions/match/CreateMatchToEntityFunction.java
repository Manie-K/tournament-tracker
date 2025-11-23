package goralczyk.maciej.models.functions.match;

import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.models.match.CreateMatchModel;
import goralczyk.maciej.repository.tournament.api.TournamentRepository;
import goralczyk.maciej.repository.user.api.UserRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.io.Serializable;
import java.util.function.Function;

public class CreateMatchToEntityFunction implements Function<CreateMatchModel, Match>, Serializable
{
    private final UserRepository userRepository;
    private final TournamentRepository tournamentRepository;

    @Inject
    public CreateMatchToEntityFunction(UserRepository userRepository, TournamentRepository tournamentRepository) {
        this.userRepository = userRepository;
        this.tournamentRepository = tournamentRepository;
    }

    @Override
    public Match apply(CreateMatchModel createMatch) {
        User userA = userRepository.findByName(createMatch.getParticipantAName()).orElse(null);
        Tournament tournament = tournamentRepository.findByName(createMatch.getTournament().getName()).orElseThrow(NotFoundException::new);

        return Match.builder()
                .id(createMatch.getId())
                .participantA(userA)
                .participantB(null)
                .tournament(tournament)
                .startDateTime(createMatch.getStartDateTime())
                .build();
    }
}
