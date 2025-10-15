package goralczyk.maciej.repository.user.memory;

import goralczyk.maciej.entity.User;
import goralczyk.maciej.repository.user.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Simple in-memory repository for User entity. Used in business layer.
 */
public class UserInMemoryRepository implements UserRepository
{
    /**
     * Underlying data store. In future should be replaced with database connection.
     */
    private final DataStore store;

    /**
     * @param store data store
     */
    public UserInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<User> find(UUID id) {
        return store.findAllUsers().stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();

    }

    @Override
    public List<User> findAll() {
        return store.findAllUsers();
    }

    @Override
    public void create(User entity) {
        store.createUser(entity);
    }

    @Override
    public void delete(User entity) {
        throw new UnsupportedOperationException("Not implemented in in-memory user repository.");
    }

    @Override
    public void update(User entity) {
        store.updateUser(entity);
    }
}
