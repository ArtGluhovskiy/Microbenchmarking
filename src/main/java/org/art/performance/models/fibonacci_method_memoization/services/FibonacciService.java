package org.art.performance.models.fibonacci_method_memoization.services;

public interface FibonacciService {

    /**
     * Calculates the n-th number in Fibonacci sequence with result memoization.
     */
    int calculateMem(int n);

    /**
     * Calculates the n-th number in Fibonacci sequence without result memoization.
     */
    int calculate(int n);
}
