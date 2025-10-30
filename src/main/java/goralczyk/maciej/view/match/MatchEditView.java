package goralczyk.maciej.view.match;

import goralczyk.maciej.dto.ModelFunctionFactory;
import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.entity.models.EditMatchModel;
import goralczyk.maciej.service.match.api.MatchService;
import goralczyk.maciej.service.tournament.api.TournamentService;
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
public class MatchEditView implements Serializable
{
    @Inject
    private MatchService matchService;
    @Inject
    private TournamentService tournamentService;

    @Inject
    private ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID matchId;

    @Getter
    @Setter
    private EditMatchModel match;

    @Getter
    @Setter
    private List<Tournament> tournaments;

    public void init() throws IOException {
        Optional<Match> matchOptional = matchService.find(matchId);
        if (matchOptional.isPresent())
        {
            this.match = factory.entityToEditModel().apply(matchOptional.get());
        }
        else
        {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Match not found");
        }

        tournaments = tournamentService.findAll();
    }

    public String saveMatch() {
        matchService.update(factory.editMatchToEntity().apply(matchService.find(matchId).orElseThrow(), match));

        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }


}
