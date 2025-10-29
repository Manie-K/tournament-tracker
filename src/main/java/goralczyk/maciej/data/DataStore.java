package goralczyk.maciej.data;

import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.utility.serialization.CloningUtility;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * For the sake of simplification instead of using real database this example is using a data source object which should
 * be put in servlet context in a single instance. In order to avoid {@link java.util.ConcurrentModificationException}
 * all methods are synchronized. Normally synchronization would be carried on by the database server. Caution, this is
 * very inefficient implementation but can be used to present other mechanisms without obscuration example with ORM
 * usage.
 */
@Log
@ApplicationScoped
@NoArgsConstructor(force = true)
public class DataStore
{
    /**
     * Set of all users.
     */
    private final Set<User> users = new HashSet<>();


    /**
     * Set of all tournaments.
     */
    private final Set<Tournament> tournaments = new HashSet<>();


    /**
     * Set of all matches.
     */
    private final Set<Match> matches = new HashSet<>();


    /**
     * Component used for creating deep copies.
     */
    private final CloningUtility cloningUtility;

    /**
     * @param cloningUtility component used for creating deep copies
     */
    @Inject
    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

    /**
     * Seeks for all users.
     *
     * @return list (can be empty) of all users
     */
    public synchronized List<User> findAllUsers() {
        return users.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    /**
     * Stores new user.
     *
     * @param value new user to be stored
     * @throws IllegalArgumentException if user with provided id already exists
     */
    public synchronized void createUser(User value) throws IllegalArgumentException {
        if (users.stream().anyMatch(user -> user.getId().equals(value.getId())))
        {
            throw new IllegalArgumentException("The user id \"%s\" is not unique".formatted(value.getId()));
        }
        users.add(cloningUtility.clone(value));
    }

    /**
     * Updates existing user.
     *
     * @param value user to be updated
     * @throws IllegalArgumentException if user with the same id does not exist
     */
    public synchronized void updateUser(User value) throws IllegalArgumentException {
        if (users.removeIf(user -> user.getId().equals(value.getId())))
        {
            users.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The user with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    /**
     * Seeks for all tournaments.
     *
     * @return list (can be empty) of all tournaments
     */
    public synchronized List<Tournament> findAllTournaments() {
        return tournaments.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    /**
     * Stores new tournament.
     *
     * @param value new tournament to be stored
     * @throws IllegalArgumentException if tournament with provided id already exists
     */
    public synchronized void createTournament(Tournament value) throws IllegalArgumentException {
        if (tournaments.stream().anyMatch(tournament -> tournament.getId().equals(value.getId())))
        {
            throw new IllegalArgumentException("The tournament id \"%s\" is not unique".formatted(value.getId()));
        }
        tournaments.add(cloningUtility.clone(value));
    }

    /**
     * Updates existing tournament.
     *
     * @param value tournament to be updated
     * @throws IllegalArgumentException if tournament with the same id does not exist
     */
    public synchronized void updateTournament(Tournament value) throws IllegalArgumentException {
        if (tournaments.removeIf(tournament -> tournament.getId().equals(value.getId())))
        {
            tournaments.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The tournament with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    /**
     * Deletes existing tournament.
     *
     * @param id id of the tournament to be deleted
     * @throws IllegalArgumentException if tournament with the same id does not exist
     */
    public synchronized void deleteTournament(UUID id) throws IllegalArgumentException
    {
        Tournament toDelete = tournaments.stream()
                .filter(tournament -> tournament.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("The tournament with id \"%s\" does not exist".formatted(id)));

        List<Match> matchesToDelete = matches.stream().filter(match -> match.getTournament().equals(toDelete)).collect(Collectors.toList());
        for (Match match : matchesToDelete) {
            matches.remove(match);
        }
        tournaments.remove(toDelete);
    }

    /**
     * Seeks for all matches.
     *
     * @return list (can be empty) of all matches
     */
    public synchronized List<Match> findAllMatches() {
        return matches.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    /**
     * Stores new match.
     *
     * @param value new match to be stored
     * @throws IllegalArgumentException if match with provided id already exists
     */
    public synchronized void createMatch(Match value) throws IllegalArgumentException {
        if (matches.stream().anyMatch(match -> match.getId().equals(value.getId())))
        {
            throw new IllegalArgumentException("The match id \"%s\" is not unique".formatted(value.getId()));
        }
        Match entityWithRelationships = cloneWithRelationships(value);
        matches.add(entityWithRelationships);
    }

    /**
     * Updates existing match.
     *
     * @param value match to be updated
     * @throws IllegalArgumentException if match with the same id does not exist
     */
    public synchronized void updateMatch(Match value) throws IllegalArgumentException {
        Match entityWithRelationships = cloneWithRelationships(value);
        if (matches.removeIf(match -> match.getId().equals(value.getId())))
        {
            matches.add(entityWithRelationships);
        } else {
            throw new IllegalArgumentException("The match with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    /**
     * Deletes existing match.
     *
     * @param id id of the match to be deleted
     * @throws IllegalArgumentException if match with the same id does not exist
     */
    public synchronized void deleteMatch(UUID id) throws IllegalArgumentException
    {
        if (!matches.removeIf(match -> match.getId().equals(id))) {
            throw new IllegalArgumentException("The match with id \"%s\" does not exist".formatted(id));
        }

    }


    /**
     * Clones existing Match and updates relationships for values in storage
     *
     * @param value Match to be cloned
     * @return cloned value with updated relationships
     * @throws IllegalArgumentException when {@link User} or {@link Tournament} with provided uuid does not exist
     */
    private Match cloneWithRelationships(Match value) {
        Match entity = cloningUtility.clone(value);

        if (entity.getParticipantA() != null) {
            entity.setParticipantA(users.stream()
                    .filter(user -> user.getId().equals(value.getParticipantA().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No user with id \"%s\".".formatted(value.getParticipantA().getId()))));
        }

        if (entity.getParticipantB() != null) {
            entity.setParticipantB(users.stream()
                    .filter(user -> user.getId().equals(value.getParticipantB().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No user with id \"%s\".".formatted(value.getParticipantB().getId()))));
        }

        if (entity.getTournament() != null) {
            entity.setTournament(tournaments.stream()
                    .filter(profession -> profession.getId().equals(value.getTournament().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No tournament with id \"%s\".".formatted(value.getTournament().getId()))));
        }

        return entity;
    }

}
