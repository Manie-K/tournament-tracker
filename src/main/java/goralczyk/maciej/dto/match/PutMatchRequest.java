package goralczyk.maciej.dto.match;

import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PutMatchRequest
{
    private UUID participantAId;
    private Tournament tournament;
    private LocalDateTime startDateTime;
    private int result = -1;
}
