package goralczyk.maciej.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tournament {
    private int id;
    private String name;
    private String location;

    private List<Match> matches; //TODO: include format e.g. single elimination bracket, List<List<Matches>>
}
