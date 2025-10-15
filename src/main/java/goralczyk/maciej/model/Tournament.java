package goralczyk.maciej.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * Model class representing a single tournament.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tournament {
    /**
     * Unique ID (primary key)
     */
    private int id;

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
