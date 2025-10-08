package goralczyk.maciej.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Match {
    private int id;
    private User participantA;
    private User participantB;
    private Tournament tournament;
    private LocalDateTime startDateTime;
    private int result = -1;
}
