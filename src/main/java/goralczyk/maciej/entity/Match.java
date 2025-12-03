package goralczyk.maciej.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Model class representing a single match.
 */
@Data
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="matches")
public class Match extends VersionAndCreationDateAuditable implements Serializable {
    /**
     * Unique ID (primary key)
     */
    @Id
    private UUID id;

    /**
     * First participant.
     */

    @ManyToOne
    @JoinColumn(name = "participant_a_id")
    private User participantA;

    /**
     * Second participant. - for now non-existing in DB
     */
    @Transient
    private User participantB;

    /**
     * Tournament in which this match takes place.
     */
    @ManyToOne
    @JoinColumn(name="tournament_id")
    private Tournament tournament;

    /**
     * Date and time of the start of the match.
     */
    private LocalDateTime startDateTime;

    /**
     * Indicates the winner.
     * -1 -> match not finished yet.
     * 0  -> participant A won.
     * 1  -> participant B won.
     */
    private int result = -1;

    /**
     * Returns null if the match hasn't concluded, User who won otherwise.
     */
    public Optional<User> getWinner()
    {
        return result == -1 ? Optional.empty() : (result == 0 ? Optional.of(participantA) : Optional.of(participantB));
    }
}
