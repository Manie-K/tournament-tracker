package goralczyk.maciej.dto.match;

import goralczyk.maciej.entity.Tournament;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchMatchRequest {
    private LocalDateTime startDateTime;
    private int result;
}
