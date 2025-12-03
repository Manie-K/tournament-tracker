package goralczyk.maciej.view.tournament;

import goralczyk.maciej.dto.ModelFunctionFactory;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.models.match.MatchModel;
import goralczyk.maciej.models.match.MatchesModel;
import goralczyk.maciej.models.tournament.TournamentModel;
import goralczyk.maciej.service.match.MatchService;
import goralczyk.maciej.service.tournament.TournamentRepository;
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
    private TournamentRepository tournamentRepository;
    @Inject
    private MatchService matchService;
    private final ModelFunctionFactory modelFunctionFactory;

    @Getter
    @Setter
    private UUID tournamentId;

    @Getter
    private TournamentModel tournament;

    @Getter
    private MatchesModel matches;

    @EJB
    public void setTournamentRepository(TournamentRepository service) {
        this.tournamentRepository = service;
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
        if(this.tournamentId == null) {
            return;
        }
        Optional<Tournament> tournamentOptional = tournamentRepository.find(tournamentId);
        if (tournamentOptional.isPresent())
        {
            this.tournament = modelFunctionFactory.tournamentToModel().apply(tournamentOptional.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Tournament not found");
        }
    }

    public MatchesModel getMatches() {
        if(matches == null)
        {
            matches = modelFunctionFactory.matchesToModel().apply(matchService.findAllByTournamentAndCaller(tournamentId));
        }
        return matches;
    }

    public void deleteMatch(UUID matchId)
    {
        matchService.delete(matchId);
        matches = null;
    }
}
