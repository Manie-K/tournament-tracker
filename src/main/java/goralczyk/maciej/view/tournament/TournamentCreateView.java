package goralczyk.maciej.view.tournament;

import goralczyk.maciej.dto.ModelFunctionFactory;
import goralczyk.maciej.models.tournament.TournamentCreateModel;
import goralczyk.maciej.service.tournament.TournamentService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@ViewScoped
@Named
@NoArgsConstructor(force = true)
public class TournamentCreateView implements Serializable {
    private final TournamentService tournamentService;

    private final ModelFunctionFactory factory;

    @Getter
    private TournamentCreateModel tournament;

    @Inject
    public TournamentCreateView(TournamentService service, ModelFunctionFactory factory) {
        this.tournamentService = service;
        this.factory = factory;
    }

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
