package org.art.performance;

import org.art.performance.models.fibonacci_method_memoization.services.CacheService;
import org.art.performance.models.fibonacci_method_memoization.services.CacheServiceImpl;
import org.art.performance.models.fibonacci_method_memoization.services.FibonacciService;
import org.art.performance.models.fibonacci_method_memoization.services.FibonacciServiceImpl;
import org.openjdk.jmh.annotations.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * Compares the performance of Fibonacci n-th sequence
 * value calculation:
 * - calculation with method result memoization;
 * - calculation without method result memoization.
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Measurement(iterations = 3)
@Warmup(iterations = 1)
@Fork(value = 2)
public class FibonacciMemoizationBenchmark {

    private static final String CONTEXT_NAME = "fibonacci-memoization-context.xml";
    private static final String CACHE_SERVICE_NAME = "cacheService";
    private static final String FIBONACCI_SERVICE_NAME = "fibonacciService";

    private static ApplicationContext context = new ClassPathXmlApplicationContext(CONTEXT_NAME);

    private static CacheService cacheService = context.getBean(CACHE_SERVICE_NAME, CacheServiceImpl.class);
    private static FibonacciService fibonacciService = context.getBean(FIBONACCI_SERVICE_NAME, FibonacciServiceImpl.class);

    @Param({"20", "25", "30", "35"})
    private int n;

    @Setup(Level.Invocation)
    public void setUp() {
        cacheService.clearCache();
    }

    @Benchmark
    public int memoizedCalculation() {
        return fibonacciService.calculateMem(n);
    }

    @Benchmark
    public int simpleCalculation() {
        return fibonacciService.calculate(n);
    }
}
