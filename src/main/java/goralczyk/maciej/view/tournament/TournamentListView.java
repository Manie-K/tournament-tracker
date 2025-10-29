package goralczyk.maciej.view.tournament;

import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.service.match.api.MatchService;
import goralczyk.maciej.service.tournament.api.TournamentService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
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

    public int getMatchesCountForTournament(UUID tournamentId)
    {
        Tournament tournament = tournamentService.find(tournamentId).orElse(null);
        return matchService.findAllByTournament(tournament).size();
    }

    public String deleteTournament(UUID tournamentId)
    {
        boolean deleted = tournamentService.delete(tournamentId);
        return "tournament_list?faces-redirect=true";
    }
}
