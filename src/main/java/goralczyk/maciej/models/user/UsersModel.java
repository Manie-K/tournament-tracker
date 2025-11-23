package goralczyk.maciej.models.user;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersModel {

    @Data
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User{
        private UUID id;
        private String login;
    }
    private List<User> users;
}
