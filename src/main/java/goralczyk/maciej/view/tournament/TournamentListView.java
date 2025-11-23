package goralczyk.maciej.view.tournament;

import goralczyk.maciej.dto.ModelFunctionFactory;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.models.tournament.TournamentsModel;
import goralczyk.maciej.service.match.MatchService;
import goralczyk.maciej.service.tournament.TournamentService;
import jakarta.ejb.EJB;
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
    private TournamentService tournamentService;
    private final ModelFunctionFactory modelFunctionFactory;

    private TournamentsModel tournaments;


    @Inject
    public TournamentListView(ModelFunctionFactory modelFunctionFactory)
    {
        this.modelFunctionFactory = modelFunctionFactory;
    }

    @EJB
    public void setTournamentService(TournamentService tournamentService)
    {
        this.tournamentService = tournamentService;
    }

    public TournamentsModel getTournaments()
    {
        if(tournaments == null)
        {
            tournaments = modelFunctionFactory.tournamentsToModel().apply(tournamentService.findAll());
        }
        return tournaments;
    }

    public String deleteTournament(UUID tournamentId)
    {
        boolean deleted = tournamentService.delete(tournamentId);
        return "tournament_list?faces-redirect=true";
    }
}
