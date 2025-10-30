package goralczyk.maciej.entity.models;

import goralczyk.maciej.entity.Tournament;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class EditMatchModel implements Serializable
{
    /**
     * First participant.
     */
    private String participantAName;

    /**
     * Second participant.
     */
    private String participantBName;

    /**
     * Tournament in which this match takes place.
     */
    private Tournament tournament;
}
