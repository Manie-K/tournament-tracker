package goralczyk.maciej.view.tournament;

import goralczyk.maciej.dto.ModelFunctionFactory;
import goralczyk.maciej.models.tournament.TournamentCreateModel;
import goralczyk.maciej.service.tournament.TournamentRepository;
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
    private final TournamentRepository tournamentRepository;

    private final ModelFunctionFactory factory;

    @Getter
    private TournamentCreateModel tournament;

    @Inject
    public TournamentCreateView(TournamentRepository service, ModelFunctionFactory factory) {
        this.tournamentRepository = service;
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
        tournamentRepository.create(factory.createTournamentToEntity().apply(tournament));
        return "tournament_list.xhtml?faces-redirect=true";
    }
}
