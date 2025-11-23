package goralczyk.maciej.models.tournament;

import goralczyk.maciej.models.match.MatchModel;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TournamentModel implements Serializable {
    /**
     * Unique ID (primary key)
     */
    private UUID id;
    private String name;
    private String location;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<MatchModel> matches;
}
