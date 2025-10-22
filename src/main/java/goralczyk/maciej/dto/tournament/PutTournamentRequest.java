package goralczyk.maciej.dto.tournament;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PutTournamentRequest {
    private String name;
    private String location;
}
