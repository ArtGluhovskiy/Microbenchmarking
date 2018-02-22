package org.art.performance.models.fork_join_pool;

import java.util.concurrent.CountedCompleter;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * Finds the max element in the array via Counted Completer API.
 */
public class CountedCompleterMaxFinder extends CountedCompleter<Integer> {

    private int[] array;
    private int lo;
    private int hi;
    private BinaryOperator<Integer> reducer = Math::max;        //Reduce function for finding the max element in the pair
    private CountedCompleterMaxFinder sibling;
    private Integer result;

    public CountedCompleterMaxFinder(CountedCompleter<Integer> completer, int[] array, int lo, int hi) {
        super(completer);
        this.array = array;
        this.lo = lo;
        this.hi = hi;
    }

    @Override
    public void compute() {
        if (hi - lo >= 2) {
            int mid = (lo + hi) >> 1;
            CountedCompleterMaxFinder left = new CountedCompleterMaxFinder(this, array, lo, mid);
            CountedCompleterMaxFinder right = new CountedCompleterMaxFinder(this, array, mid, hi);
            left.sibling = right;
            right.sibling = left;
            setPendingCount(1);         //only right is pending
            right.fork();
            left.compute();
        } else {
            if (hi > lo) {
                result = findMax(lo, hi, array);
            }
            tryComplete();
        }
    }

    @Override
    public void onCompletion(CountedCompleter<?> caller) {
        if (caller != this) {
            CountedCompleterMaxFinder child = (CountedCompleterMaxFinder) caller;
            CountedCompleterMaxFinder sib = child.sibling;
            if (sib == null || sib.result == 0) {
                result = child.result;
            } else {
                result = reducer.apply(child.result, sib.result);
            }
        }
    }

    @Override
    public Integer getRawResult() {
        return this.result;
    }

    /**
     * Finds the max element on the specified interval (from 'lo' to 'hi') in the array.
     */
    private static int findMax(int lo, int hi, int[] array) {
        int elem;
        int max = array[lo];
        if (hi > lo) {
            for (int i = lo; i < hi - 1; i++) {
                if ((elem = array[lo + 1]) > max) {
                    max = elem;
                }
            }
        }
        return max;
    }
}

