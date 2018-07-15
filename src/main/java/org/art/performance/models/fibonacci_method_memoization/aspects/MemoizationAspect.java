package org.art.performance.models.fibonacci_method_memoization.aspects;

import org.art.performance.models.fibonacci_method_memoization.annotations.Memoize;
import org.art.performance.models.fibonacci_method_memoization.pojo.InvocationContext;
import org.art.performance.models.fibonacci_method_memoization.services.CacheService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Provides the 'memoize' functionality for methods
 * annotated with {@link Memoize} annotation by means
 * of method result caching in order to avoid repeatable
 * time consuming processing.
 */
@Aspect
@Component
public class MemoizationAspect {

    private CacheService cache;

    @Autowired
    public MemoizationAspect(CacheService cache) {
        this.cache = cache;
    }

    @Around("execution(* *(..)) && @annotation(org.art.performance.models.fibonacci_method_memoization.annotations.Memoize)")
    public Object memoizeResult(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        Class<?> targetClass = joinPoint.getSignature().getDeclaringType();
        String targetMethod = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        InvocationContext invocationContext = new InvocationContext(targetClass, targetMethod, args);
        Object memoizedResult = cache.get(invocationContext);
        if (memoizedResult != null) {
            result = memoizedResult;
        } else {
            result = joinPoint.proceed();
            cache.put(invocationContext, result);
        }
        return result;
    }
}
