package goralczyk.maciej.dto.user;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Response DTO for retrieving user details.
 */
@Data
@Builder
public class GetUserResponse
{
    @Data
    @Builder
    public static class MatchSummary {
        private UUID id;
    }
    /**
     * Unique ID (primary key)
     */
    private UUID id;

    /**
     * Name of the user.
     */
    private String name;

    private String login;

    private String email;

    /**
     * Date of birth of the user.
     */
    private LocalDate dateOfBirth;

    /**
     * Role of the user, either Normal or Admin.
     */
    private String role;

    /**
     * Matches in which the user participated.
     */
    private List<MatchSummary> matches;
}
