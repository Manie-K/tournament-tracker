package goralczyk.maciej.configuration.listener;

import goralczyk.maciej.configuration.StringConstants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.Produces;

/**
 * Provides photo directory path.
 */
@ApplicationScoped

public class PhotoDirectoryProducer {

    @Inject
    private ServletContext servletContext;

    @Produces
    @Named("photoDirectory")
    @ApplicationScoped
    public String getPhotoDirectory() {
        String photoDir = servletContext.getInitParameter(StringConstants.PHOTO_DIR);
        if (photoDir == null || photoDir.isEmpty()) {
            throw new IllegalStateException("Photo upload directory is not configured.");
        }
        return photoDir;
    }
}
