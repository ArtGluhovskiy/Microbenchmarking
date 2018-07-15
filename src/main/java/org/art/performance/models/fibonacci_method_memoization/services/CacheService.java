package org.art.performance.models.fibonacci_method_memoization.services;

import org.art.performance.models.fibonacci_method_memoization.pojo.CacheStatistics;
import org.art.performance.models.fibonacci_method_memoization.pojo.InvocationContext;

public interface CacheService {

    void put(InvocationContext invocationContext, Object result);

    Object get(InvocationContext invocationContext);

    CacheStatistics getCacheStatistics();

    int getCacheSize();

    int getCurrentCacheSize();

    void clearCache();
}
