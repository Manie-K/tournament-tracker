package goralczyk.maciej.dto.tournament.function;

import goralczyk.maciej.dto.tournament.PutTournamentRequest;
import goralczyk.maciej.entity.Tournament;

import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToTournamentFunction implements BiFunction<UUID, PutTournamentRequest, Tournament> {
    @Override
    public Tournament apply(UUID uuid, PutTournamentRequest putTournamentRequest) {
        return Tournament.builder()
                .id(uuid)
                .name(putTournamentRequest.getName())
                .location(putTournamentRequest.getLocation())
                .matches(List.of()) // New tournament has no matches
                .build();
    }
}
