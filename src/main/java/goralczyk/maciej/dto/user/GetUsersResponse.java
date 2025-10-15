package goralczyk.maciej.dto.user;

import lombok.*;

import java.util.List;
import java.util.UUID;


/**
 * Response DTO for retrieving a list of users.
 */
@Data
@Builder
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor
public class GetUsersResponse
{
    @Data
    @AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
    @NoArgsConstructor
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
