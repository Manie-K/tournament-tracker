package goralczyk.maciej.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


/**
 * Model class representing user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {
    /**
     * Unique ID (primary key)
     */
    private UUID id;

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
    private Role role;

    /**
     * Matches in which the user participated.
     */
    private List<Match> matches;

    /**
     * Photo of the user.
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] photo;

    /**
     * Returns the age of the user based on the date of birth.
     * @return age in years
     */
    public int getAge() {
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }
}
