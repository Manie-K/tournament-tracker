package goralczyk.maciej.dto.match;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class GetMatchResponse {
    @Data
    @Builder
    public static class UserSummary {
        private String name;
    }

    @Data
    @Builder
    public static class TournamentSummary {
        private String name;
    }

    private UUID id;
    private UserSummary participantA;
    private UserSummary participantB;
    private TournamentSummary tournament;
}
