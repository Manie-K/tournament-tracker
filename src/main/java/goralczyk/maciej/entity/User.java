package goralczyk.maciej.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


/**
 * Model class representing user.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {
    /**
     * Unique ID (primary key)
     */
    @Id
    private UUID id;

    /**
     * Name of the user.
     */
    private String name;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false, unique = true)
    private String email;

    @ToString.Exclude
    @Column(nullable = false)
    private String password;

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
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "participantA", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Match> matches;

    /**
     * Photo of the user.
     */
    @Transient
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] photo;


}
