package goralczyk.maciej.dto.tournament;

import goralczyk.maciej.entity.Tournament;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetTournamentsResponse
{
    @Data
    @Builder
    public static class SingleTournament {
        private String name;
        private String location;
    }

    private List<SingleTournament> tournaments;
}
