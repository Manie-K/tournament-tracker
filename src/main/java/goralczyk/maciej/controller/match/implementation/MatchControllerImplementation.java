package goralczyk.maciej.controller.match.implementation;

import goralczyk.maciej.controller.match.api.MatchController;
import goralczyk.maciej.controller.servlet.exception.NotFoundException;
import goralczyk.maciej.dto.DtoFunctionFactory;
import goralczyk.maciej.dto.match.GetMatchResponse;
import goralczyk.maciej.dto.match.GetMatchesResponse;
import goralczyk.maciej.dto.match.PatchMatchRequest;
import goralczyk.maciej.dto.match.PutMatchRequest;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.service.match.api.MatchService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@RequestScoped
public class MatchControllerImplementation implements MatchController
{

    private final DtoFunctionFactory dtoFactory;
    private final MatchService matchService;

    @Inject
    public MatchControllerImplementation(DtoFunctionFactory dtoFactory, MatchService matchService) {
        this.dtoFactory = dtoFactory;
        this.matchService = matchService;
    }

    @Override
    public GetMatchesResponse getMatches() {
        return dtoFactory.matchesToResponse().apply(matchService.findAll());
    }

    @Override
    public GetMatchesResponse getUserMatches(User user) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public GetMatchesResponse getTournamentMatches(Tournament tournament) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public GetMatchResponse getMatch(UUID uuid) {
        return matchService.find(uuid)
                .map(dtoFactory.matchToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putMatch(UUID id, PutMatchRequest request) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void patchMatch(UUID id, PatchMatchRequest request) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void deleteMatch(UUID id) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
