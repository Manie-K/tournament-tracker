package goralczyk.maciej.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;


/**
 * Model class representing a single tournament.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tournament implements Serializable {
    /**
     * Unique ID (primary key)
     */
    private UUID id;

    /**
     * Name of the tournament.
     */
    private String name;

    /**
     * Location of the tournament.
     */
    private String location;

    /**
     * List of matches in this tournament.
     */
    private List<Match> matches; //TODO: include format e.g. single elimination bracket, List<List<Matches>>
}
