package goralczyk.maciej.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


/**
 * Model class representing user.
 */
@Data
@AllArgsConstructor
public class User {
    /**
     * Unique ID (primary key)
     */
    private int id;

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
}
