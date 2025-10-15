package goralczyk.maciej.configuration.listener;

import goralczyk.maciej.configuration.StringConstants;
import goralczyk.maciej.data.DataStore;
import goralczyk.maciej.utility.serialization.CloningUtility;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Listener started automatically on servlet context initialized. Creates an instance of datasource and puts it in the
 * application (servlet) context.
 */
@WebListener//using annotation does not allow configuring order
public class CreateDataSource implements ServletContextListener
{
    @Override
    public void contextInitialized(ServletContextEvent event) {
        event.getServletContext().setAttribute(StringConstants.DATA_SOURCE, new DataStore(new CloningUtility()));
    }

}
