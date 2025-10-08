package goralczyk.maciej.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
public class User {
    private int id;
    private String name;
    private LocalDate dateOfBirth;
    private Role role;
    private List<Match> matches;
}
