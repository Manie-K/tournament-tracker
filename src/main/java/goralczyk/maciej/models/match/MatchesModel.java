package goralczyk.maciej.models.match;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchesModel implements Serializable {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Match {
        private UUID id;
        private String participantAName;
        private String tournamentName;
    }

    private List<Match> matches;
}
