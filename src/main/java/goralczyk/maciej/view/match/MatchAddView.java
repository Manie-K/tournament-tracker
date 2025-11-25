package goralczyk.maciej.view.match;

import java.io.IOException;
import java.io.Serializable;

import goralczyk.maciej.dto.ModelFunctionFactory;
import goralczyk.maciej.models.match.CreateMatchModel;
import goralczyk.maciej.models.tournament.TournamentModel;
import goralczyk.maciej.service.match.MatchService;
import goralczyk.maciej.service.tournament.TournamentRepository;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Named
@ViewScoped
@NoArgsConstructor(force = true)
public class MatchAddView implements Serializable
{
    private MatchService matchService;
    private TournamentRepository tournamentRepository;

    private final ModelFunctionFactory modelFunctionFactory;

    @Getter
    @Setter
    private CreateMatchModel match;

    @Getter
    @Setter
    private List<TournamentModel> tournaments;

    @Inject
    public MatchAddView(ModelFunctionFactory modelFunctionFactory) {
        this.modelFunctionFactory = modelFunctionFactory;
    }

    @EJB
    public void setMatchService(MatchService matchService) {
        this.matchService = matchService;
    }

    @EJB
    public void setTournamentRepository(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public void init() throws IOException {
        match = CreateMatchModel.builder()
                .id(UUID.randomUUID())
                .build();

        tournaments = tournamentRepository.findAll().stream().map(modelFunctionFactory.tournamentToModel()).toList();
    }

    public String addMatch() {
        matchService.createByCaller(modelFunctionFactory.createMatchToEntity().apply(match));
        return "match_detail?faces-redirect=true&amp;matchId=" + match.getId();
    }
}
