package goralczyk.maciej.dto.tournament.function;

import goralczyk.maciej.dto.tournament.GetTournamentResponse;
import goralczyk.maciej.dto.user.GetUserResponse;
import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.repository.match.api.MatchRepository;
import goralczyk.maciej.service.match.MatchService;
import goralczyk.maciej.service.user.api.UserService;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TournamentToResponseFunction implements Function<Tournament, GetTournamentResponse> {

    private final MatchService matchService;

    public TournamentToResponseFunction(MatchService  matchService) {this.matchService = matchService;}

    @Override
    public GetTournamentResponse apply(Tournament tournament) {
        List<Match> matches = matchService.findAllByTournament(tournament.getId());

        List<GetTournamentResponse.MatchSummary> matchesSummaries = new ArrayList<>();
        for (Match match : matches) {
            String nameA;
            String nameB;

            if(match.getParticipantA() == null){
                nameA = "nullA";
            }else{
                nameA = match.getParticipantA().getName();
            }

            if(match.getParticipantB() == null){
                nameB = "nullB";
            }else{
                nameB = match.getParticipantB().getName();
            }

            GetTournamentResponse.MatchSummary matchSummary = GetTournamentResponse.MatchSummary.builder()
                    .id(match.getId())
                    .playerOne(nameA)
                    .playerTwo(nameB)
                    .build();
            matchesSummaries.add(matchSummary);
        }

        return GetTournamentResponse.builder()
                .id(tournament.getId())
                .name(tournament.getName())
                .location(tournament.getLocation())
                .matches(matchesSummaries)
                .build();
    }
}
