package goralczyk.maciej.dto.match;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class GetMatchesResponse {
    @Data
    @Builder
    public static class UserSummary {
        private String name;
    }

    @Data
    @Builder
    public static class SingleMatch {
        private UUID id;
        private UserSummary participantA;
        private UserSummary participantB;
    }

    private List<SingleMatch> matches;
}
