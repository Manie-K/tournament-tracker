package goralczyk.maciej.view.match;

import java.io.IOException;
import java.io.Serializable;

import goralczyk.maciej.dto.ModelFunctionFactory;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.entity.models.CreateMatchModel;
import goralczyk.maciej.service.match.api.MatchService;
import goralczyk.maciej.service.tournament.api.TournamentService;
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
    private List<Tournament> tournaments;

    public void init() throws IOException {
        match = CreateMatchModel.builder()
                .id(UUID.randomUUID())
                .build();

        tournaments = tournamentService.findAll();
    }

    public String addMatch() {
        matchService.create(modelFunctionFactory.createMatchToEntity().apply(match));
        return "tournament_detail?faces-redirect=true&amp;tournamentId=" + match.getTournament().getId();
    }
}
