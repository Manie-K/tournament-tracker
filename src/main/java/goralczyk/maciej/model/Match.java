package goralczyk.maciej.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Model class representing a single match.
 */
@Data
@AllArgsConstructor
public class Match {
    /**
     * Unique ID (primary key)
     */
    private int id;

    /**
     * First participant.
     */
    private User participantA;

    /**
     * Second participant.
     */
    private User participantB;

    /**
     * Tournament in which this match takes place.
     */
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
}
