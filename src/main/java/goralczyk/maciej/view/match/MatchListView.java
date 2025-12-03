package goralczyk.maciej.view.match;

import goralczyk.maciej.dto.ModelFunctionFactory;
import goralczyk.maciej.models.match.MatchesModel;
import goralczyk.maciej.service.match.MatchService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@Named
@RequestScoped
public class MatchListView  implements Serializable {

    @Inject
    private MatchService matchService;
    private final ModelFunctionFactory factory;

    @Getter
    private MatchesModel matches;

    @Inject
    public MatchListView(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setMatchService(MatchService matchService) {
        this.matchService = matchService;
    }

    public MatchesModel getMatches()
    {
        if(matches == null)
        {
            matches = factory.matchesToModel().apply(matchService.findAllByCaller());
        }

        return matches;
    }

    public void deleteMatch(UUID id)
    {
        boolean deleted = matchService.deleteByCaller(id);
        matches = null; // reset matches to force reload
    }
}
