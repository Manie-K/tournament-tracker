package goralczyk.maciej.view.match;

import java.io.IOException;
import java.io.Serializable;

import goralczyk.maciej.entity.Match;
import goralczyk.maciej.service.match.api.MatchService;
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

    @Getter
    @Setter
    private UUID matchId;

    @Getter
    private Match match;

    public void init() throws IOException {
        Optional<Match> matchOptional = matchService.find(matchId);

        if (matchOptional.isPresent())
        {
            this.match = matchOptional.get();
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Match not found");
        }

    }
}
