package goralczyk.maciej.authorization.interceptors;

import goralczyk.maciej.authorization.bindings.LogOperation;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.security.enterprise.SecurityContext;

import java.util.logging.Logger;

@Interceptor
@LogOperation
@Priority(10)
public class LogInterceptor {

    private final SecurityContext securityContext;

    private static final Logger logger = Logger.getLogger(LogInterceptor.class.getName());

    @Inject
    public LogInterceptor(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception
    {
        String annotation = context.getMethod().getAnnotation(LogOperation.class).value();

        String name = securityContext.getCallerPrincipal() != null
                ? securityContext.getCallerPrincipal().getName() : "ANONYMOUS";

        Object resourceId = context.getParameters().length > 0
                ? context.getParameters()[0] : "none";

        logger.info(() -> String.format("User=['%s'] Operation=['%s'] Resource=['%s']", name, annotation, resourceId));

        return context.proceed();
    }

}