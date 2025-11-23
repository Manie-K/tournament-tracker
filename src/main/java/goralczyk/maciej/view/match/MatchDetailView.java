package goralczyk.maciej.view.match;

import java.io.IOException;
import java.io.Serializable;

import goralczyk.maciej.dto.ModelFunctionFactory;
import goralczyk.maciej.entity.Match;
import goralczyk.maciej.models.match.MatchModel;
import goralczyk.maciej.service.match.MatchService;
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
    @Inject
    private MatchService matchService;

    @Inject
    private ModelFunctionFactory modelFunctionFactory;

    @Getter
    @Setter
    private UUID matchId;

    @Getter
    private MatchModel match;

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
