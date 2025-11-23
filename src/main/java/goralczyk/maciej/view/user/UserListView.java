package goralczyk.maciej.view.user;

import goralczyk.maciej.dto.ModelFunctionFactory;
import goralczyk.maciej.models.user.UsersModel;
import goralczyk.maciej.service.user.UserService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.UUID;

@RequestScoped
@Named
public class UserListView {

    private final UserService userService;

    private UsersModel users;

    private final ModelFunctionFactory factory;

    @Inject
    public UserListView(UserService service, ModelFunctionFactory factory) {
        this.userService = service;
        this.factory = factory;
    }

    public UsersModel getUsers() {
        if (users == null) {
            users = factory.usersToModel().apply(userService.findAll());
        }
        return users;
    }

    public String delete(UUID id) {
        userService.delete(id);
        return "user_list?faces-redirect=true";
    }

}