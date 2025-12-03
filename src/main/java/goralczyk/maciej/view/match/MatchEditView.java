package goralczyk.maciej.view.match;

import goralczyk.maciej.dto.ModelFunctionFactory;
import goralczyk.maciej.entity.Match;
import goralczyk.maciej.models.match.EditMatchModel;
import goralczyk.maciej.service.match.MatchService;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@Named
@ViewScoped
public class MatchEditView implements Serializable
{
    private MatchService matchService;
    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID matchId;

    @Getter
    @Setter
    private EditMatchModel match;

    private final FacesContext facesContext;

    @Inject
    public MatchEditView(ModelFunctionFactory factory, FacesContext facesContext) {
        this.factory = factory;
        this.facesContext = facesContext;
    }

    @EJB
    public void setMatchService(MatchService matchService) {
        this.matchService = matchService;
    }

    public void init() throws IOException {
        Optional<Match> matchOptional = matchService.findByCaller(matchId);
        if (matchOptional.isPresent())
        {
            this.match = factory.entityToEditModel().apply(matchOptional.get());
        }
        else
        {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Match not found");
        }
    }

    public String saveMatch() throws IOException {
        try{
            matchService.updateByCaller(factory.editMatchToEntity().apply(matchService.findByCaller(matchId).orElseThrow(), match));
            String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            return viewId + "?faces-redirect=true&includeViewParams=true";
        }catch (Exception ex) {
            if (ex.getCause() instanceof OptimisticLockException) {
                init();
                facesContext.addMessage(null, new FacesMessage("Version collision."));
            }
            return null;
        }
    }


}
