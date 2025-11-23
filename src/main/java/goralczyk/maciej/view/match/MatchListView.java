package goralczyk.maciej.view.match;

import goralczyk.maciej.dto.ModelFunctionFactory;
import goralczyk.maciej.models.match.MatchesModel;
import goralczyk.maciej.service.match.MatchService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.UUID;

@Named
@ViewScoped
public class MatchListView  implements Serializable {

    @Inject
    private MatchService matchService;

    @Inject
    private MatchesModel matches;

    @Inject
    private ModelFunctionFactory factory;

    public MatchesModel getMatches()
    {
        if(matches == null)
        {
            matches = factory.matchesToModel().apply(matchService.findAllByCaller());
        }

        return matches;
    }

    public String deleteMatch(UUID id)
    {
        boolean deleted = matchService.deleteByCaller(id);
        return "matches_list?faces-redirect=true";
    }
}
