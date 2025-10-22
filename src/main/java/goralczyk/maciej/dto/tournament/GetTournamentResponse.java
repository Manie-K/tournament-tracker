package goralczyk.maciej.dto.tournament;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class GetTournamentResponse {
    @Data
    @Builder
    public static class MatchSummary {
        private String playerOne;
        private String playerTwo;
    }

    private UUID id;
    private String name;
    private String location;
    private List<MatchSummary> matches;
}
