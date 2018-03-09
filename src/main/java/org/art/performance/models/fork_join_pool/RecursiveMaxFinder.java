package org.art.performance.models.fork_join_pool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Finds the max element in the array via Recursive Task API.
 */
public class RecursiveMaxFinder extends RecursiveTask<Integer> {

    private int[] array;
    private int lo;
    private int hi;

    public RecursiveMaxFinder(int[] array, int lo, int hi) {
        this.array = array;
        this.lo = lo;
        this.hi = hi;
    }

    @Override
    public Integer compute() {
        int result = 0;
        if (hi - lo >= 2) {
            int mid = (lo + hi) >> 1;
            result =  ForkJoinTask.invokeAll(createSubtasks(lo, hi, mid))
                    .stream()
                    .mapToInt(ForkJoinTask::join)
                    .max()
                    .orElse(0);
        } else {
            if (hi > lo) {
                result = findMax(lo, hi, array);
            }
        }
        return result;
    }

    private Collection<RecursiveMaxFinder> createSubtasks(int lo, int hi, int mid) {
        List<RecursiveMaxFinder> dividedTasks = new ArrayList<>();
        RecursiveMaxFinder right = new RecursiveMaxFinder(array, lo, mid);
        RecursiveMaxFinder left = new RecursiveMaxFinder(array, mid, hi);
        dividedTasks.add(right);
        dividedTasks.add(left);
        return dividedTasks;
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
