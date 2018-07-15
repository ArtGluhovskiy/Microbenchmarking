package org.art.performance.models.fibonacci_method_memoization.services;

import org.art.performance.models.fibonacci_method_memoization.annotations.Memoize;
import org.springframework.aop.framework.AopContext;

public class FibonacciServiceImpl implements FibonacciService {

    /**
     * Calculates the n-th number in Fibonacci sequence with result memoization.
     */
    @Memoize
    @Override
    public int calculateMem(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        /*
          Note: It is necessary to use explicit 'current proxy' calls to 'detect'
          recursive method invocations by Spring aspects. This solution totally
          couples the code to Spring AOP, and it makes the class itself aware of
          the fact that it is being used in an AOP context.
         */
        return ((FibonacciServiceImpl) AopContext.currentProxy()).calculateMem(n - 2) +
                ((FibonacciServiceImpl) AopContext.currentProxy()).calculateMem(n - 1);
    }

    /**
     * Calculates the n-th number in Fibonacci sequence without result memoization.
     */
    @Override
    public int calculate(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        return calculate(n - 2) + calculate(n - 1);
    }
}
