package goralczyk.maciej.configuration.listener;

import goralczyk.maciej.configuration.StringConstants;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.Produces;

/**
 * Provides photo directory path.
 */
@ApplicationScoped

public class PhotoDirectoryProducer
{
    @Inject
    private ServletContext servletContext;

    private String photoDirectory;


    @PostConstruct
    public void init() {
        photoDirectory = servletContext.getInitParameter(StringConstants.PHOTO_DIR);
        if (photoDirectory == null || photoDirectory.isEmpty()) {
            throw new IllegalStateException("Photo upload directory is not configured.");
        }
    }

    @Produces
    @Named("photoDirectory")
    public String getPhotoDirectory() {
        return photoDirectory;
    }
}
