package goralczyk.maciej.configuration.listener;

import goralczyk.maciej.configuration.StringConstants;
import goralczyk.maciej.data.DataStore;
import goralczyk.maciej.repository.user.api.UserRepository;
import goralczyk.maciej.repository.user.memory.UserInMemoryRepository;
import goralczyk.maciej.service.user.implementation.UserServiceImplementation;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Listener started automatically on servlet context initialized. Creates an instance of services (business layer) and
 * puts them in the application (servlet) context.
 */
@WebListener//using annotation does not allow configuring order
public class CreateServices implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        DataStore dataSource = (DataStore) event.getServletContext().getAttribute(StringConstants.DATA_SOURCE);
        UserRepository userRepository = new UserInMemoryRepository(dataSource);

        event.getServletContext().setAttribute(StringConstants.USER_SERVICE, new UserServiceImplementation(userRepository));
    }

}
