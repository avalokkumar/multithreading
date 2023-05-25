package com.clay.g_parallel_processing_parallelism.parallel_sorting;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * In this example, we use the Fork/Join framework to implement parallel sorting. Here's a breakdown of the code:
 *
 * We create an array of integers to be sorted.
 * We create a ForkJoinPool with the desired parallelism level.
 * We define a RecursiveAction subclass named ParallelSortAction to represent the parallel sorting task.
 * The ParallelSortAction class has a constructor to specify the array, start index, and end index to be sorted.
 * In the compute method of ParallelSortAction, we check if the array size is smaller than a threshold (in this case, 10). If so, we perform sequential sorting using Arrays.sort.
 * If the array size is larger, we split the array into two halves
 *
 */
public class ParallelSortingExample {

    public static void main(String[] args) {
        int[] array = {5, 2, 9, 1, 7, 4, 6, 3, 8};

        // Create a ForkJoinPool with the desired parallelism level
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        // Create an instance of the RecursiveAction subclass for parallel sorting
        ParallelSortAction sortAction = new ParallelSortAction(array, 0, array.length - 1);

        // Execute the parallel sorting task using the ForkJoinPool
        forkJoinPool.invoke(sortAction);

        // Print the sorted array
        System.out.println("Sorted array: " + Arrays.toString(array));
    }

    // RecursiveAction subclass for parallel sorting
    static class ParallelSortAction extends RecursiveAction {
        private final int[] array;
        private final int start;
        private final int end;

        ParallelSortAction(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            // If the array size is smaller than a threshold, perform sequential sorting
            if (end - start <= 10) {
                Arrays.sort(array, start, end + 1);
            } else {
                // Split the array into two halves
                int mid = start + (end - start) / 2;

                // Create two parallel sorting tasks for the two halves
                ParallelSortAction leftSort = new ParallelSortAction(array, start, mid);
                ParallelSortAction rightSort = new ParallelSortAction(array, mid + 1, end);

                // Invoke the parallel sorting tasks
                invokeAll(leftSort, rightSort);

                // Merge the sorted halves
                merge(array, start, mid, end);
            }
        }

        // Merge helper method to combine two sorted halves of the array
        private void merge(int[] array, int start, int mid, int end) {
            int[] merged = new int[end - start + 1];
            int i = start;
            int j = mid + 1;
            int k = 0;

            while (i <= mid && j <= end) {
                if (array[i] <= array[j]) {
                    merged[k++] = array[i++];
                } else {
                    merged[k++] = array[j++];
                }
            }

            while (i <= mid) {
                merged[k++] = array[i++];
            }

            while (j <= end) {
                merged[k++] = array[j++];
            }

            System.arraycopy(merged, 0, array, start, merged.length);
        }
    }
}
