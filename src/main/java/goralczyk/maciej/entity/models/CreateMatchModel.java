package goralczyk.maciej.entity.models;

import goralczyk.maciej.entity.Tournament;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CreateMatchModel implements Serializable {
    /**
     * Unique ID (primary key)
     */
    private UUID id;

    /**
     * First participant.
     */
    private String participantAName;

    /**
     * Second participant.
     */
    private String participantBName;

    /**
     * Tournament in which this match takes place.
     */
    private Tournament tournament;

    /**
     * Date and time of the start of the match.
     */
    private LocalDateTime startDateTime;
}
