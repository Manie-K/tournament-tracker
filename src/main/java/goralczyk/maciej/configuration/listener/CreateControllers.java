package goralczyk.maciej.configuration.listener;

import goralczyk.maciej.configuration.StringConstants;
import goralczyk.maciej.controller.user.implementation.UserSimpleController;
import goralczyk.maciej.dto.DtoFunctionFactory;
import goralczyk.maciej.service.user.api.UserService;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.ServletContextEvent;

/**
 * Listener started automatically on servlet context initialized. Creates an instance of controllers and puts them in
 * the application (servlet) context.
 */
@WebListener//using annotation does not allow configuring order
public class CreateControllers implements ServletContextListener
{
    @Override
    public void contextInitialized(ServletContextEvent event)
    {
        UserService userService = (UserService) event.getServletContext().getAttribute(StringConstants.USER_SERVICE);

        event.getServletContext().setAttribute(StringConstants.USER_CONTROLLER, new UserSimpleController(
                userService,
                new DtoFunctionFactory()
        ));
    }
}
