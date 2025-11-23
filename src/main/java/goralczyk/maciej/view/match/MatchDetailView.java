package goralczyk.maciej.view.match;

import java.io.IOException;
import java.io.Serializable;

import goralczyk.maciej.dto.ModelFunctionFactory;
import goralczyk.maciej.entity.Match;
import goralczyk.maciej.models.match.MatchModel;
import goralczyk.maciej.service.match.MatchService;
import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
import java.util.UUID;

@Named
@ViewScoped
public class MatchDetailView implements Serializable
{
    private MatchService matchService;
    private final ModelFunctionFactory modelFunctionFactory;

    @Getter
    @Setter
    private UUID matchId;

    @Getter
    private MatchModel match;

    @Inject
    public MatchDetailView(ModelFunctionFactory modelFunctionFactory)
    {
        this.modelFunctionFactory = modelFunctionFactory;
    }

    @EJB
    public void setMatchService(MatchService matchService) {
        this.matchService = matchService;
    }

    public void init() throws IOException {
        Optional<Match> matchOptional = matchService.find(matchId);

        if (matchOptional.isPresent())
        {
            this.match = modelFunctionFactory.matchToModel().apply(matchOptional.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Match not found");
        }

    }
}
