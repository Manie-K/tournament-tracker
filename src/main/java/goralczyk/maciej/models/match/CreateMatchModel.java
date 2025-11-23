package goralczyk.maciej.models.match;

import goralczyk.maciej.models.tournament.TournamentModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
     * Tournament in which this match takes place.
     */
    private TournamentModel tournament;

    /**
     * Date and time of the start of the match.
     */
    private LocalDateTime startDateTime;
}
