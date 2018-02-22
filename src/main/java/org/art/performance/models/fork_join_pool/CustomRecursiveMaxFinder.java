package org.art.performance.models.fork_join_pool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Finds the max element in the array via Recursive Task API.
 */
public class CustomRecursiveMaxFinder extends RecursiveTask<Integer> {

    private int[] array;
    private int lo;
    private int hi;

    public CustomRecursiveMaxFinder(int[] array, int lo, int hi) {
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

    private Collection<CustomRecursiveMaxFinder> createSubtasks(int lo, int hi, int mid) {
        List<CustomRecursiveMaxFinder> dividedTasks = new ArrayList<>();
        CustomRecursiveMaxFinder right = new CustomRecursiveMaxFinder(array, lo, mid);
        CustomRecursiveMaxFinder left = new CustomRecursiveMaxFinder(array, mid, hi);
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
