package goralczyk.maciej.configuration.singleton;

import goralczyk.maciej.entity.Role;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.repository.user.api.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@NoArgsConstructor(force = true)
public class InitializeAdminService {

    /**
     * User service.
     */
    private final UserRepository userRepository;

    /**
     * Hash mechanism used for storing users' passwords.
     */
    private final Pbkdf2PasswordHash passwordHash;

    /**
     * @param userRepository user repository
     * @param passwordHash   hash mechanism used for storing users' passwords
     */
    @Inject
    public InitializeAdminService(
            UserRepository userRepository,
            @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash
    ) {
        this.userRepository = userRepository;
        this.passwordHash = passwordHash;
    }

    /**
     * Initializes database with some example values. Should be called after creating this object. This object should be
     * created only once.
     */
    @PostConstruct
    @SneakyThrows
    private void init() {
        if (userRepository.findByLogin("admin-service").isEmpty()) {

            User admin = User.builder()
                    .id(UUID.fromString("14d59f3a-057c-44d5-825a-19295a6600a8"))
                    .login("admin-service")
                    .name("Admin")
                    .email("admin-service@tournamenttracker.example.com")
                    .password(passwordHash.generate("adminservice".toCharArray()))
                    .dateOfBirth(LocalDate.EPOCH)
                    .role(Role.ADMIN)
                    .build();

            userRepository.create(admin);
        }
    }

}
