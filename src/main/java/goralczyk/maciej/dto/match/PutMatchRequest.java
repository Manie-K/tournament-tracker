package goralczyk.maciej.dto.match;

import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class PutMatchRequest
{
    private User participantA;
    private User participantB;
    private Tournament tournament;
    private LocalDateTime startDateTime;
    private int result = -1;
}
