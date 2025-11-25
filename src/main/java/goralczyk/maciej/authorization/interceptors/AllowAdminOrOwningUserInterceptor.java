package goralczyk.maciej.authorization.interceptors;

import goralczyk.maciej.authorization.bindings.AllowAdminOrOwningUser;
import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.Role;
import goralczyk.maciej.exception.InvalidRoleException;
import goralczyk.maciej.exception.NullCallerException;
import goralczyk.maciej.service.match.MatchService;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.security.enterprise.SecurityContext;

import java.util.Optional;
import java.util.UUID;

@Interceptor
@AllowAdminOrOwningUser
@Priority(10)
public class AllowAdminOrOwningUserInterceptor {
    private final SecurityContext securityContext;

    private final MatchService matchService;

    @Inject
    public AllowAdminOrOwningUserInterceptor(SecurityContext securityContext, MatchService matchService) {
        this.securityContext = securityContext;
        this.matchService = matchService;
    }

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception
    {
        if (securityContext.getCallerPrincipal() == null)
        {
            throw new NullCallerException();
        }

        if (authorized(context))
        {
            return context.proceed();
        }

        throw new InvalidRoleException();
    }

    private boolean authorized(InvocationContext context)
    {
        if (securityContext.isCallerInRole(Role.ADMIN))
        {
            return true;
        }
        else if (securityContext.isCallerInRole(Role.USER))
        {
            Object obj = context.getParameters()[0];
            Optional<Match> match;
            if (obj instanceof UUID)
            {
                match = matchService.find((UUID)obj);
            }
            else if (obj instanceof Match)
            {
                match = matchService.find(((Match)obj).getId());
            }
            else
            {
                throw new IllegalStateException();
            }

            return match.isPresent()
                    && match.get().getParticipantA().getLogin().equals(securityContext.getCallerPrincipal().getName());
        }
        return false;
    }
}
