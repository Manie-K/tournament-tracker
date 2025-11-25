package goralczyk.maciej.view.tournament;

import goralczyk.maciej.dto.ModelFunctionFactory;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.models.tournament.TournamentEditModel;
import goralczyk.maciej.service.tournament.TournamentRepository;
import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class TournamentEditView implements Serializable {

    private TournamentRepository tournamentRepository;

    private final ModelFunctionFactory factory;

    @Getter
    @Setter
    private UUID tournamentId;

    @Getter
    private TournamentEditModel tournament;

    @Inject
    public TournamentEditView(ModelFunctionFactory modelFunctionFactory)
    {
        this.factory = modelFunctionFactory;
    }

    @EJB
    public void setTournamentRepository(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public void init() throws IOException {
        Optional<Tournament> t = tournamentRepository.find(tournamentId);
        if (t.isPresent()) {
            this.tournament = factory.tournamentToEditModel().apply(t.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Tournament not found");
        }
    }

    public String save() {
        tournamentRepository.update(factory.editTournamentToTournament().apply(tournamentRepository.find(tournamentId).orElseThrow(), tournament));
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}
