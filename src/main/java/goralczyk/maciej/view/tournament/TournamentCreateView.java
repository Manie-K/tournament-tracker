package goralczyk.maciej.view.tournament;

import goralczyk.maciej.dto.ModelFunctionFactory;
import goralczyk.maciej.models.tournament.TournamentCreateModel;
import goralczyk.maciej.service.tournament.TournamentService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.UUID;

@ViewScoped
@Named
public class TournamentCreateView implements Serializable {
    @Inject
    private TournamentService tournamentService;

    @Inject
    private ModelFunctionFactory factory;

    private TournamentCreateModel tournament;

    public void init() {
        if (tournament == null) {
            tournament = TournamentCreateModel.builder()
                    .id(UUID.randomUUID())
                    .build();
        }

    }

    public String cancel() {
        return "tournament_list.xhtml?faces-redirect=true";
    }

    public String save() {
        tournamentService.create(factory.createTournamentToEntity().apply(tournament));
        return "tournament_list.xhtml?faces-redirect=true";
    }
}
