package goralczyk.maciej.view.converter;

import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.service.tournament.TournamentService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.UUID;

@FacesConverter(value = "tournamentConverter", managed = true)
@ApplicationScoped
public class TournamentConverter implements Converter<Tournament>
{
    @Inject
    private TournamentService tournamentService;

    @Override
    public Tournament getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value == null || value.trim().isEmpty())
        {
            return null;
        }

        try
        {
            UUID id = UUID.fromString(value.trim());
            return tournamentService.find(id).orElse(null);
        } catch (IllegalArgumentException e)
        {
            throw new ConverterException("Invalid tournament ID: " + value, e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Tournament value)
    {
        if (value == null || value.getId() == null)
        {
            return "";
        }

        return value.getId().toString();
    }
}
