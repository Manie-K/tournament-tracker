package goralczyk.maciej.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Model class representing a single match.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="matches")
public class Match implements Serializable {
    /**
     * Unique ID (primary key)
     */
    @Id
    private UUID id;

    /**
     * First participant.
     */
    @Transient
    private User participantA;

    /**
     * Second participant.
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
