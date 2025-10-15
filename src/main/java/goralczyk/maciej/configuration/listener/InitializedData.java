package goralczyk.maciej.configuration.listener;

import goralczyk.maciej.configuration.StringConstants;
import goralczyk.maciej.entity.Role;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.service.user.api.UserService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Listener started automatically on servlet context initialized. Fetches instance of the datasource from the servlet
 * context and fills it with default content. Normally this class would fetch database datasource and init data only in
 * cases of empty database. When using persistence storage application instance should be initialized only during first
 * run in order to init database with starting data. Good place to create first default admin user.
 */
@WebListener//using annotation does not allow configuring order
public class InitializedData implements ServletContextListener
{
    /**
     * User service.
     */
    private UserService userService;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        userService = (UserService) event.getServletContext().getAttribute(StringConstants.USER_SERVICE);
        init();
    }

    /**
     * Initializes database with some example values. Should be called after creating this object. This object should be
     * created only once.
     */
    @SneakyThrows
    private void init()
    {
        User admin = User.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .name("Admin")
                .dateOfBirth(LocalDate.EPOCH)
                .role(Role.Admin)
                .matches(List.of())
                .photo(null)
                .build();

        User user = User.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                .name("User")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .role(Role.Normal)
                .matches(List.of())
                .photo(null)
                .build();

        User me = User.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000003"))
                .name("Maciej")
                .dateOfBirth(LocalDate.of(2003, 11, 20))
                .role(Role.Normal)
                .matches(List.of())
                .photo(null)
                .build();

        User lewandowski = User.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000004"))
                .name("Robert Lewandowski")
                .dateOfBirth(LocalDate.of(1988, 8, 21))
                .role(Role.Normal)
                .matches(List.of())
                .photo(null)
                .build();

        userService.create(admin);
        userService.create(user);
        userService.create(me);
        userService.create(lewandowski);
    }

    /**
     * @param name name of the desired resource
     * @return array of bytes read from the resource
     */
    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            if (is != null) {
                return is.readAllBytes();
            } else {
                throw new IllegalStateException("Unable to get resource %s".formatted(name));
            }
        }
    }

}
