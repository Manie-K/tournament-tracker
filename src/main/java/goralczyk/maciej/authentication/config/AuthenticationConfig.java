package goralczyk.maciej.authentication.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = "Tournament Tracker")
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/TournamentTracker",
        callerQuery = "select password from users where login = ?",
        groupsQuery = "select role from users where login = ?",
        hashAlgorithm = Pbkdf2PasswordHash.class
)
public class AuthenticationConfig {
}
