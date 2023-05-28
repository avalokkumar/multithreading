package com.clay.g_parallel_processing_parallelism.parallel_reduction;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelReductionExample {

    public static void main(String[] args) {
        // Create a ForkJoinPool with parallelism level equal to the number of available processors
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        // Create a large array of numbers
        int[] numbers = {5, 8, 2, 10, 4, 7, 3, 9, 6, 1};

        // Calculate the sum of all numbers using parallel reduction
        int sum = forkJoinPool.invoke(new SumTask(numbers, 0, numbers.length));

        System.out.println("Sum: " + sum);
    }

    private static class SumTask extends RecursiveTask<Integer> {
        private int[] numbers;
        private int start;
        private int end;
        private static final int THRESHOLD = 1000;

        public SumTask(int[] numbers, int start, int end) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start <= THRESHOLD) {
                // If the problem size is small enough, compute the sum directly
                int sum = 0;
                for (int i = start; i < end; i++) {
                    sum += numbers[i];
                }
                return sum;
            } else {
                // If the problem size is larger, split it into smaller subtasks and invoke them in parallel
                int mid = (start + end) / 2;
                SumTask leftTask = new SumTask(numbers, start, mid);
                SumTask rightTask = new SumTask(numbers, mid, end);

                leftTask.fork(); // Fork the left task asynchronously
                int rightSum = rightTask.compute(); // Compute the right task immediately

                int leftSum = leftTask.join(); // Wait for the left task to complete and retrieve its result

                // Return the combined result of the left and right subtasks
                return leftSum + rightSum;
            }
        }
    }
}
