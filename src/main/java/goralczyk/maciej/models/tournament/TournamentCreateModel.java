package goralczyk.maciej.models.tournament;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TournamentCreateModel implements Serializable {
    /**
     * Unique ID (primary key)
     */
    private UUID id;

    /**
     * Name of the tournament.
     */
    private String name;

    /**
     * Location of the tournament.
     */
    private String location;
}
