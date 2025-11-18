package goralczyk.maciej.dto;

import goralczyk.maciej.dto.match.function.MatchToResponseFunction;
import goralczyk.maciej.dto.match.function.MatchesToResponseFunction;
import goralczyk.maciej.dto.match.function.RequestToMatchFunction;
import goralczyk.maciej.dto.match.function.UpdateMatchWithRequestFunction;
import goralczyk.maciej.dto.tournament.function.RequestToTournamentFunction;
import goralczyk.maciej.dto.tournament.function.TournamentToResponseFunction;
import goralczyk.maciej.dto.tournament.function.TournamentsToResponseFunction;
import goralczyk.maciej.dto.tournament.function.UpdateTournamentWithRequest;
import goralczyk.maciej.dto.user.GetUserResponse;
import goralczyk.maciej.dto.user.GetUsersResponse;
import goralczyk.maciej.dto.user.PutUserRequest;
import goralczyk.maciej.dto.user.function.RequestToUserFunction;
import goralczyk.maciej.dto.user.function.UpdateUserWithRequestFunction;
import goralczyk.maciej.dto.user.function.UserToResponseFunction;
import goralczyk.maciej.dto.user.function.UsersToResponseFunction;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.service.match.MatchService;
import goralczyk.maciej.service.user.api.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.function.Function;

/**
 * Factory for creating {@link Function} implementation for converting between various objects used in different layers.
 * Instead of injecting multiple function objects single factory is injected.
 */
@ApplicationScoped
public class DtoFunctionFactory
{
    @Inject
    private MatchService matchService;

    /**
     * Returns a function to convert a list of {@link User} to {@link GetUsersResponse}.
     *
     * @return UsersToResponseFunction instance
     */
    public UsersToResponseFunction usersToResponse() {
        return new UsersToResponseFunction();
    }

    /**
     * Returns a function to convert a single {@link User} to {@link GetUserResponse}.
     *
     * @return UserToResponseFunction instance
     */
    public UserToResponseFunction userToResponse() {
        return new UserToResponseFunction();
    }

    /**
     * Returns a function to convert a {@link PutUserRequest} to a {@link User}.
     *
     * @return RequestToUserFunction instance
     */
    public RequestToUserFunction requestToUser() {
        return new RequestToUserFunction();
    }

    /**
     * Returns a function to update a {@link User}.
     *
     * @return UpdateUserFunction instance
     */
    public UpdateUserWithRequestFunction updateUser() {
        return new UpdateUserWithRequestFunction();
    }

    public TournamentsToResponseFunction tournamentsToResponse() {return new TournamentsToResponseFunction();}
    public TournamentToResponseFunction tournamentToResponse() {return new TournamentToResponseFunction(matchService);}
    public RequestToTournamentFunction requestToTournament() {return new RequestToTournamentFunction();}
    public UpdateTournamentWithRequest updateTournament() {return new UpdateTournamentWithRequest(matchService);}

    public MatchesToResponseFunction matchesToResponse() {return new MatchesToResponseFunction();}
    public MatchToResponseFunction matchToResponse() {return new MatchToResponseFunction();}
    public RequestToMatchFunction requestToMatch() {return new RequestToMatchFunction();}
    public UpdateMatchWithRequestFunction updateMatch() {return new UpdateMatchWithRequestFunction();}

}
