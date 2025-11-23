package goralczyk.maciej.models.tournament;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TournamentsModel implements Serializable {

    @Data
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Tournament {
        private UUID id;
        private String name;
    }

    private List<Tournament> tournaments;
}
