package goralczyk.maciej.authorization.interceptors;

import goralczyk.maciej.authorization.bindings.AllowRoles;
import goralczyk.maciej.exception.InvalidRoleException;
import goralczyk.maciej.exception.NullCallerException;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.security.enterprise.SecurityContext;

@Interceptor
@AllowRoles
@Priority(10)
public class AllowRolesInterceptor {
    private final SecurityContext securityContext;

    @Inject
    public AllowRolesInterceptor(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception
    {
        if (securityContext.getCallerPrincipal() == null)
        {
            throw new NullCallerException();
        }

        String[] roles = context.getMethod().getAnnotation(AllowRoles.class).value();
        for (String r : roles)
        {
            if (securityContext.isCallerInRole(r))
            {
                return context.proceed();
            }
        }

        throw new InvalidRoleException();
    }
}
