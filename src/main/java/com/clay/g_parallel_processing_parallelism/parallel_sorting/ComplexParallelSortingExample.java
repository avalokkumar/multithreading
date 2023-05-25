package com.clay.g_parallel_processing_parallelism.parallel_sorting;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ComplexParallelSortingExample {

    public static void main(String[] args) {
        int[] array = generateRandomArray(1000);
        System.out.println("Original array: " + Arrays.toString(array));

        parallelSort(array);
        System.out.println("Sorted array: " + Arrays.toString(array));
    }

    private static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000);
        }
        return array;
    }

    private static void parallelSort(int[] array) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ParallelSortTask task = new ParallelSortTask(array);
        forkJoinPool.invoke(task);
    }

    private static class ParallelSortTask extends RecursiveAction {
        private static final int THRESHOLD = 100;

        private int[] array;
        private int start;
        private int end;

        public ParallelSortTask(int[] array) {
            this(array, 0, array.length - 1);
        }

        private ParallelSortTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start <= THRESHOLD) {
                // Sort the small portion of the array
                Arrays.sort(array, start, end + 1);
            } else {
                // Split the task into smaller subtasks
                int mid = (start + end) / 2;
                ParallelSortTask leftTask = new ParallelSortTask(array, start, mid);
                ParallelSortTask rightTask = new ParallelSortTask(array, mid + 1, end);
                invokeAll(leftTask, rightTask);

                // Merge the sorted subarrays
                merge(array, start, mid, end);
            }
        }

        private void merge(int[] array, int start, int mid, int end) {
            int[] mergedArray = new int[end - start + 1];
            int i = start;
            int j = mid + 1;
            int k = 0;

            while (i <= mid && j <= end) {
                if (array[i] < array[j]) {
                    mergedArray[k] = array[i];
                    i++;
                } else {
                    mergedArray[k] = array[j];
                    j++;
                }
                k++;
            }

            while (i <= mid) {
                mergedArray[k] = array[i];
                i++;
                k++;
            }

            while (j <= end) {
                mergedArray[k] = array[j];
                j++;
                k++;
            }

            System.arraycopy(mergedArray, 0, array, start, mergedArray.length);
        }
    }
}

