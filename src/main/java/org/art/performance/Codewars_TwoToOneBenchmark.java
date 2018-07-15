package org.art.performance;

import org.art.performance.models.codewars.TwoToOne;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

/**
 * Codewars task. Finding the string with sorted characters
 * from two strings.
 */
@State(Scope.Thread)
public class Codewars_TwoToOneBenchmark {

    private String s1 = "dskiglmccekiiifkdllgdksuudfjsdkcmjruj";

    private String s2 = "dkfieifglodufnccnd";

    @Benchmark
    public String getStringMethod1() {
        return TwoToOne.longest1(s1, s2);
    }

    @Benchmark
    public String getStringMethod2() {
        return TwoToOne.longest2(s1, s2);
    }
}
