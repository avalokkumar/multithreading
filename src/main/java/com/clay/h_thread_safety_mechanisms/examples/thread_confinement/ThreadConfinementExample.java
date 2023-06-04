package com.clay.h_thread_safety_mechanisms.examples.thread_confinement;

import java.util.ArrayList;
import java.util.List;

/**
 * Explanation:
 *
 * * We start by creating a shared List<Integer> called numbers to store the numbers.
 * * Three threads are created: addThread to add numbers to the list, removeThread to remove numbers from the list,
 * and printThread to print the numbers from the list.
 * * Each thread operates on its own local copy of the numbers list, ensuring thread confinement.
 * * The addNumber method adds a number to the local list.
 * * The removeNumber method removes the last number from the local list if it is not empty.
 * * The printNumbers method prints all the numbers from the local list.
 * * The threads are started, and they perform their respective operations concurrently.
 * * Each thread operates on its own copy of the list, ensuring thread safety and avoiding conflicts between threads.
 *
 * Thread confinement ensures that each thread has its own local copy of the data, eliminating the need for explicit synchronization mechanisms. This approach simplifies the code and reduces the risk of concurrency issues.
 *
 */
public class ThreadConfinementExample {
    public static void main(String[] args) {
        // Create a shared list to store numbers
        List<Integer> numbers = new ArrayList<>();

        // Create three threads to concurrently add and remove numbers
        Thread addThread = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                addNumber(numbers, i);
            }
        });

        Thread removeThread = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                removeNumber(numbers);
            }
        });

        Thread printThread = new Thread(() -> {
            printNumbers(numbers);
        });

        // Start the threads
        addThread.start();
        removeThread.start();
        printThread.start();
    }

    // Thread-confinement: Each thread has its own local list
    private static void addNumber(List<Integer> numbers, int number) {
        // Perform operations on the local list
        numbers.add(number);
    }

    private static void removeNumber(List<Integer> numbers) {
        // Perform operations on the local list
        if (!numbers.isEmpty()) {
            numbers.remove(numbers.size() - 1);
        }
    }

    private static void printNumbers(List<Integer> numbers) {
        // Perform operations on the local list
        for (Integer number : numbers) {
            System.out.println(number);
        }
    }
}
