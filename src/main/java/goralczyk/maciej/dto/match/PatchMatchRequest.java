package goralczyk.maciej.dto.match;

import goralczyk.maciej.entity.Tournament;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PatchMatchRequest {
    private LocalDateTime startDateTime;
    private int result;
}
