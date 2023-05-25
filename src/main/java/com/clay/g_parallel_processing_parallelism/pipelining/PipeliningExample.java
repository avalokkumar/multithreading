package com.clay.g_parallel_processing_parallelism.pipelining;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * In this example, we have a PipeliningExample class that demonstrates the pipelining technique using Java's CompletableFuture. We simulate a complex data processing pipeline with four stages of computation:
 *
 * Stage 1: Multiply each input by 2
 * Stage 2: Filter even numbers
 * Stage 3: Square each number
 * Stage 4: Sum all numbers
 *
 * Each stage of computation is represented by a CompletableFuture, which allows us to perform the computation asynchronously. The thenApplyAsync method is used to define the next stage of computation, creating a pipeline of tasks.
 *
 * In the processPipeline method, we define each stage using the CompletableFuture.supplyAsync and thenApplyAsync methods. Each stage performs a specific operation on the input data and produces a result that is passed to the next stage.
 *
 * Each stage is executed asynchronously, and the pipeline flows through the stages concurrently. The System.out.println statements in each stage indicate the parallel execution of stages by printing the thread name.
 *
 */
public class PipeliningExample {

    public static void main(String[] args) {
        // Simulating a list of input data
        List<Integer> inputData = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        try {
            // Execute the pipeline in parallel
            List<Integer> result = processPipeline(inputData);

            System.out.println("Final Result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static List<Integer> processPipeline(List<Integer> inputData) throws InterruptedException, ExecutionException {
        // Stage 1: Multiply each input by 2
        CompletableFuture<List<Integer>> stage1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Stage 1: Multiply by 2 (Thread: " + Thread.currentThread().getName() + ")");
            return inputData.stream()
                    .map(num -> num * 2)
                    .collect(Collectors.toList());
        });

        // Stage 2: Filter even numbers
        CompletableFuture<List<Integer>> stage2 = stage1.thenApplyAsync(result -> {
            System.out.println("Stage 2: Filter even numbers (Thread: " + Thread.currentThread().getName() + ")");
            return result.stream()
                    .filter(num -> num % 2 == 0)
                    .collect(Collectors.toList());
        });

        // Stage 3: Square each number
        CompletableFuture<List<Integer>> stage3 = stage2.thenApplyAsync(result -> {
            System.out.println("Stage 3: Square each number (Thread: " + Thread.currentThread().getName() + ")");
            return result.stream()
                    .map(num -> num * num)
                    .collect(Collectors.toList());
        });

        // Stage 4: Sum all numbers
        CompletableFuture<Integer> stage4 = stage3.thenApplyAsync(result -> {
            System.out.println("Stage 4: Sum all numbers (Thread: " + Thread.currentThread().getName() + ")");
            return result.stream()
                    .reduce(0, Integer::sum);
        });

        // Wait for the final result
        return Collections.singletonList(stage4.get());
    }
}

