package goralczyk.maciej.view.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "localDateTimeConverter")
public class LocalDateTimeConverter implements Converter<LocalDateTime>
{
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    @Override
    public LocalDateTime getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return LocalDateTime.parse(s, formatter);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, LocalDateTime localDateTime) {
        if(localDateTime == null) {
            return "";
        }

        return localDateTime.format(formatter);
    }
}
