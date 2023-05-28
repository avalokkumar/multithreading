package com.clay.g_parallel_processing_parallelism.task_parallelism_work_stealing;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class TaskParallelismExample {

    public static void main(String[] args) {
        // Create a ForkJoinPool with parallelism level equal to the number of available processors
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        // Create a complex problem to solve (finding the sum of squares of numbers in a large array)
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10_000, 10_001, 10_002, /* ... */ 1_000_000};
        int sum = forkJoinPool.invoke(new SumOfSquaresTask(numbers, 0, numbers.length));

        System.out.println("Sum of squares: " + sum);
    }

    private static class SumOfSquaresTask extends RecursiveTask<Integer> {
        private int[] numbers;
        private int start;
        private int end;
        private static final int THRESHOLD = 10_000;

        public SumOfSquaresTask(int[] numbers, int start, int end) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start <= THRESHOLD) {
                // If the problem size is small enough, compute the sum of squares directly
                int sum = 0;
                for (int i = start; i < end; i++) {
                    sum += numbers[i] * numbers[i];
                }
                return sum;
            } else {
                // If the problem size is larger, split it into smaller subtasks and invoke them in parallel
                int mid = (start + end) / 2;
                SumOfSquaresTask leftTask = new SumOfSquaresTask(numbers, start, mid);
                SumOfSquaresTask rightTask = new SumOfSquaresTask(numbers, mid, end);

                leftTask.fork(); // Fork the left task asynchronously
                int rightSum = rightTask.compute(); // Compute the right task immediately

                int leftSum = leftTask.join(); // Wait for the left task to complete and retrieve its result

                // Return the combined result of the left and right subtasks
                return leftSum + rightSum;
            }
        }
    }
}

