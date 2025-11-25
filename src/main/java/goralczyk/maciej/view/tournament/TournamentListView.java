package goralczyk.maciej.view.tournament;

import goralczyk.maciej.dto.ModelFunctionFactory;
import goralczyk.maciej.models.tournament.TournamentsModel;
import goralczyk.maciej.service.tournament.TournamentRepository;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.UUID;

@Named
@RequestScoped
public class TournamentListView implements Serializable
{
    private TournamentRepository tournamentRepository;
    private final ModelFunctionFactory modelFunctionFactory;

    private TournamentsModel tournaments;


    @Inject
    public TournamentListView(ModelFunctionFactory modelFunctionFactory)
    {
        this.modelFunctionFactory = modelFunctionFactory;
    }

    @EJB
    public void setTournamentService(TournamentRepository tournamentRepository)
    {
        this.tournamentRepository = tournamentRepository;
    }

    public TournamentsModel getTournaments()
    {
        if(tournaments == null)
        {
            tournaments = modelFunctionFactory.tournamentsToModel().apply(tournamentRepository.findAll());
        }
        return tournaments;
    }

    public void deleteTournament(UUID tournamentId)
    {
        boolean deleted = tournamentRepository.delete(tournamentId);
        tournaments = null; // Refresh the tournaments list
    }
}
