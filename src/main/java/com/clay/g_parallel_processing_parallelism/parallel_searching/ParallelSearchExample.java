package com.clay.g_parallel_processing_parallelism.parallel_searching;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * In this example, we have a list of numbers and we want to search for a specific target value in parallel. The list is divided into smaller sublists, and each sublist is searched concurrently by separate threads. The search is performed by the ParallelSearchTask class, which implements the Callable interface to support returning the result from each thread.
 *
 * The main logic splits the list into sublists, submits each sublist to a thread in the thread pool, and collects the search results. If the target is found in any sublist, the program prints the index where it was found. If the target is not found in any sublist, it prints a message indicating that the target was not found.
 */
public class ParallelSearchExample {

    public static void main(String[] args) {
        List<Integer> numbers = generateRandomList(1000);
        System.out.println(numbers);
        int target = 42;

        // Split the list into smaller sublists for parallel search
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        List<Future<Integer>> futures = new ArrayList<>();

        int sublistSize = numbers.size() / numThreads;
        for (int i = 0; i < numThreads; i++) {
            int startIndex = i * sublistSize;
            int endIndex = (i == numThreads - 1) ? numbers.size() : (i + 1) * sublistSize;
            List<Integer> sublist = numbers.subList(startIndex, endIndex);
            Future<Integer> future = executorService.submit(new ParallelSearchTask(sublist, target, startIndex));
            futures.add(future);
        }

        // Collect the search results from all the threads
        for (Future<Integer> future : futures) {
            try {
                int result = future.get();
                if (result != -1) {
                    System.out.println("Target found at index: " + result);
                    return;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Target not found in the list.");
        executorService.shutdown();
    }

    private static List<Integer> generateRandomList(int size) {
        Random random = new Random();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(random.nextInt(100));
        }
        return list;
    }

    private static class ParallelSearchTask implements Callable<Integer> {
        private List<Integer> list;
        private int target;
        private int startIndex;

        public ParallelSearchTask(List<Integer> list, int target, int startIndex) {
            this.list = list;
            this.target = target;
            this.startIndex = startIndex;
        }

        @Override
        public Integer call() {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == target) {
                    return startIndex + i; // Return the absolute index in the original list
                }
            }
            return -1; // Target not found in this sublist
        }
    }
}
