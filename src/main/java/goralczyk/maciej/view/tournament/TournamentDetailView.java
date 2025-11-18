package goralczyk.maciej.view.tournament;

import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.service.match.MatchService;
import goralczyk.maciej.service.tournament.TournamentService;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Named
@ViewScoped
public class TournamentDetailView implements Serializable
{

    @Inject
    private TournamentService tournamentService;

    @Inject
    private MatchService matchService;

    @Getter
    @Setter
    private UUID tournamentId;

    @Getter
    private Tournament tournament;

    @Getter
    private List<Match> matches;

    public void init() throws IOException {
        Optional<Tournament> tournamentOptional = tournamentService.find(tournamentId);
        if (tournamentOptional.isPresent())
        {
            this.tournament = tournamentOptional.get();
            this.matches = matchService.findAllByTournament(tournament.getId());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Tournament not found");
        }
    }

    public String deleteMatch(UUID matchId)
    {
        matchService.delete(matchId);
        return "tournament_detail?faces-redirect=true&amp;tournamentId=" + tournamentId;
    }
}
