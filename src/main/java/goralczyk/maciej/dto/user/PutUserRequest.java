package goralczyk.maciej.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Request for creating a user.
 */

@Data
@Builder
public class PutUserRequest
{
    /**
     * Name of the user.
     */
    private String name;

    /**
     * Date of birth of the user.
     */
    private LocalDate dateOfBirth;

    /**
     * Role of the user, either Normal or Admin.
     */
    private String role;
}
