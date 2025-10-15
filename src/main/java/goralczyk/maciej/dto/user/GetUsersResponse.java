package goralczyk.maciej.dto.user;

import lombok.*;

import java.util.List;
import java.util.UUID;


/**
 * Response DTO for retrieving a list of users.
 */
@Data
@Builder
public class GetUsersResponse
{
    @Data
    @Builder
    public static class SingleUser
    {
        /**
         * Unique ID (primary key)
         */
        private UUID id;

        /**
         * Name of the user.
         */
        private String name;
    }

    /**
     * List of users.
     */
    @Singular
    private List<SingleUser> users;
}
