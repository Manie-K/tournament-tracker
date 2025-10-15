package goralczyk.maciej.repository.user.api;

import goralczyk.maciej.entity.User;
import goralczyk.maciej.repository.api.Repository;

import java.util.UUID;

/**
 * Repository for User entity. Used in business layer.
 */

public interface UserRepository extends Repository<User, UUID>
{

}
