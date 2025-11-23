package goralczyk.maciej.view.tournament;

import goralczyk.maciej.dto.ModelFunctionFactory;
import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.models.match.MatchModel;
import goralczyk.maciej.models.tournament.TournamentModel;
import goralczyk.maciej.service.match.MatchService;
import goralczyk.maciej.service.tournament.TournamentService;
import jakarta.ejb.EJB;
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
import java.util.stream.Collectors;

@Named
@ViewScoped
public class TournamentDetailView implements Serializable
{
    @Inject
    private TournamentService tournamentService;
    @Inject
    private MatchService matchService;
    private final ModelFunctionFactory modelFunctionFactory;

    @Getter
    @Setter
    private UUID tournamentId;

    @Getter
    private TournamentModel tournament;

    @Getter
    private List<MatchModel> matches;

    @EJB
    public void setTournamentService(TournamentService service) {
        this.tournamentService = service;
    }

    @EJB
    public void setMatchService(MatchService service) {
        this.matchService = service;
    }

    @Inject
    public TournamentDetailView(ModelFunctionFactory modelFunctionFactory) {
        this.modelFunctionFactory = modelFunctionFactory;
    }

    public void init() throws IOException {
        Optional<Tournament> tournamentOptional = tournamentService.find(tournamentId);
        if (tournamentOptional.isPresent())
        {
            this.tournament = modelFunctionFactory.tournamentToModel().apply(tournamentOptional.get());
            this.matches = matchService.findAllByTournament(tournamentId).stream()
                .map(modelFunctionFactory.matchToModel())
                .collect(Collectors.toList());;
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
