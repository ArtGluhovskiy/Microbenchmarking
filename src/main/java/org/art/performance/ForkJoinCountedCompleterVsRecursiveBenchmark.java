package org.art.performance;

import org.art.performance.models.fork_join_pool.CountedCompleterMaxFinder;
import org.art.performance.models.fork_join_pool.RecursiveMaxFinder;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.Random;

/**
 * Fork/Join Task Benchmark. Finding the max element in the array.
 * Recursive Task vs Counted Completer API.
 */
@State(Scope.Thread)
public class ForkJoinCountedCompleterVsRecursiveBenchmark {

    private int[] array;

    @Param({"100"})
    private int k;

    @Setup(Level.Trial)
    public void setUp() {
        Random rnd = new Random(System.currentTimeMillis());
        array = new int[k];
        for (int i = 0; i < k; i++) {
            array[i] = rnd.nextInt(1000);
        }
        System.out.println("Init array: " + Arrays.toString(array));
    }

    @Benchmark
    public Integer findMaxRecursiveTask() {
        RecursiveMaxFinder recursiveMaxFinder = new RecursiveMaxFinder(array, 0, array.length);
        return recursiveMaxFinder.compute();
    }

    @Benchmark
    public Integer findMaxCountedCompleterTask() {
        CountedCompleterMaxFinder completerMaxFinder = new CountedCompleterMaxFinder(null, array, 0, array.length);
        return completerMaxFinder.invoke();
    }
}
