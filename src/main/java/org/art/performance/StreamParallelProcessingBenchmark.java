package org.art.performance;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@State(Scope.Thread)
public class StreamParallelProcessingBenchmark {

    @Param({"1", "10", "100", "1000", "10000", "1000000"})
    private int n;

    private final int payload = 50;

    private List<Integer> integerList;

    @Setup(Level.Trial)
    public void setUp() {
        integerList = IntStream.range(0, n).boxed().collect(toList());
    }

    @Benchmark
    public void iterative(Blackhole bh) {
        for (Integer i : integerList) {
            Blackhole.consumeCPU(payload);
            bh.consume(i);
        }
    }

    @Benchmark
    public Optional<Integer> sequential() {
        return integerList.stream()
                .filter(l -> {
                    Blackhole.consumeCPU(payload);
                    return false;
                })
                .findFirst();
    }

    @Benchmark
    public Optional<Integer> parallel() {
        return integerList.parallelStream()
                .filter(l -> {
                    Blackhole.consumeCPU(payload);
                    return false;
                })
                .findFirst();
    }
}
