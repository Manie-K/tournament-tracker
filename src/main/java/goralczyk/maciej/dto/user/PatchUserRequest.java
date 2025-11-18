package goralczyk.maciej.dto.user;

import lombok.Builder;
import lombok.Data;

/**
 * Request for updating a user.
 */
@Data
@Builder
public class PatchUserRequest
{
    /**
     * Name of the user.
     */
    private String name;
    private String login;
    private String email;
}
