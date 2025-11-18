package goralczyk.maciej.view.tournament;

import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.service.match.MatchService;
import goralczyk.maciej.service.tournament.TournamentService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Named
@RequestScoped
public class TournamentListView implements Serializable
{
    @Inject
    private TournamentService tournamentService;

    @Inject
    private MatchService matchService;

    public List<Tournament> getTournaments()
    {
        return tournamentService.findAll();
    }

    public String deleteTournament(UUID tournamentId)
    {
        boolean deleted = tournamentService.delete(tournamentId);
        return "tournament_list?faces-redirect=true";
    }
}
