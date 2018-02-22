package org.art.performance;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Objects;
import java.util.Scanner;

/**
 * Launches a benchmark from the 'main' method.
 * Available benchmark IDs:
 * 'StreamParallelProcessingBenchmark' - 1
 * 'ForkJoinCountedCompleterVsRecursiveBenchmark' - 2
 */
public class BenchmarkLauncher {

    private static void runBenchmark(Class<?> clazz) throws RunnerException {
        Objects.requireNonNull(clazz);
        Options opt = new OptionsBuilder()
                .include(clazz.getSimpleName())
                .warmupIterations(1)
                .measurementIterations(10)
                .build();

        new Runner(opt).run();
    }

    private static int getBenchmarkId() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Benchmark ID: <Integer value>:");
        return scanner.nextInt();
    }

    public static void main(String[] args) throws RunnerException {
        int benchmarkId = getBenchmarkId();
        Class<?> clazz;
        switch (benchmarkId) {
            case 1:
                System.out.println("'StreamParallelProcessingBenchmark' is running.");
                clazz = StreamParallelProcessingBenchmark.class;
                break;
            case 2:
                System.out.println("'ForkJoinCountedCompleterVsRecursiveBenchmark' is running.");
                clazz = ForkJoinCountedCompleterVsRecursiveBenchmark.class;
                break;
            default:
                System.out.println("Wrong Benchmark ID!");
                clazz = null;
        }
        if (clazz != null) {
            runBenchmark(clazz);
        }
    }
}
