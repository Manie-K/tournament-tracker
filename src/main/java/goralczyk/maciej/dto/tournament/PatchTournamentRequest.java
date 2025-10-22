package goralczyk.maciej.dto.tournament;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatchTournamentRequest {
    private String name;
    private String location;
}
