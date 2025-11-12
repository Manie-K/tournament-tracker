package goralczyk.maciej.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;


/**
 * Model class representing a single tournament.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tournaments")
public class Tournament implements Serializable {
    /**
     * Unique ID (primary key)
     */
    @Id
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
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Match> matches; //TODO: include format e.g. single elimination bracket, List<List<Matches>>
}
