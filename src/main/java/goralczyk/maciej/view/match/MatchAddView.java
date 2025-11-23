package goralczyk.maciej.view.match;

import java.io.IOException;
import java.io.Serializable;

import goralczyk.maciej.dto.ModelFunctionFactory;
import goralczyk.maciej.models.match.CreateMatchModel;
import goralczyk.maciej.models.tournament.TournamentModel;
import goralczyk.maciej.service.match.MatchService;
import goralczyk.maciej.service.tournament.TournamentService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Named
@ViewScoped
public class MatchAddView implements Serializable
{
    @Inject
    private MatchService matchService;

    @Inject
    private TournamentService tournamentService;

    @Inject
    private ModelFunctionFactory modelFunctionFactory;

    @Getter
    @Setter
    private CreateMatchModel match;

    @Getter
    @Setter
    private List<TournamentModel> tournaments;

    public void init() throws IOException {
        match = CreateMatchModel.builder()
                .id(UUID.randomUUID())
                .build();

        tournaments = tournamentService.findAll().stream().map(modelFunctionFactory.tournamentToModel()).toList();
    }

    public String addMatch() {
        matchService.createByCaller(modelFunctionFactory.createMatchToEntity().apply(match));
        return "match_detail?faces-redirect=true&amp;matchId=" + match.getId();
    }
}
