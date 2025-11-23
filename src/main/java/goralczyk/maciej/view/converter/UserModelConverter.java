package goralczyk.maciej.view.converter;

import goralczyk.maciej.dto.ModelFunctionFactory;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.models.user.UserModel;
import goralczyk.maciej.service.user.UserService;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.UUID;

/**
 * Faces converter for {@link UserModel}. The managed attribute in {@link @FacesConverter} allows the converter to be
 * the CDI bean. In previous version of JSF converters were always created inside JSF lifecycle and where not managed by
 * container that is injection was not possible. As this bean is not annotated with scope the beans.xml descriptor must
 * be present.
 */
@FacesConverter(forClass = UserModel.class, managed = true)
public class UserModelConverter implements Converter<UserModel> {

    /**
     * Service for users management.
     */
    private final UserService service;

    /**
     * Factory producing functions for conversion between models and entities.
     */
    private final ModelFunctionFactory factory;


    /**
     * @param service service for users management
     * @param factory factory producing functions for conversion between models and entities
     */
    @Inject
    public UserModelConverter(UserService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public UserModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<User> user = service.find(UUID.fromString(value));
        return user.map(factory.userToModel()).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, UserModel value) {
        return value == null ? "" : value.getLogin();
    }

}

