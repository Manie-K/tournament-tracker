package goralczyk.maciej.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request for updating a user.
 */
@Data
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor
public class PatchUserRequest
{
    /**
     * Name of the user.
     */
    private String name;
}
