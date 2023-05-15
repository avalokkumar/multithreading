package com.clay.c_thread_safety_synchronization.syn_methods.cyclic_barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * In this example, the Race class represents a multi-stage race with 4 runners. It uses a CyclicBarrier to coordinate the start and finish of the race.
 *
 * The constructor initializes two CyclicBarrier objects: startBarrier and finishBarrier. The startBarrier is initialized with NUM_RUNNERS parties and a barrier action that gets executed when all runners are ready to start. The finishBarrier is also initialized with NUM_RUNNERS parties and a barrier action that gets executed when all runners have crossed the finish line.
 *
 * The startRace method simulates the race. It creates a thread for each runner and starts them. Each runner waits at the startBarrier until all runners are ready. After that, they simulate running for a random amount of time. Once a runner crosses the finish line, they wait at the finishBarrier until all runners have finished the race.
 *
 */
public class Race {
    private static final int NUM_RUNNERS = 4;
    private final CyclicBarrier startBarrier;
    private final CyclicBarrier finishBarrier;

    public Race() {
        startBarrier = new CyclicBarrier(NUM_RUNNERS, () -> {
            System.out.println("All runners are ready. The race starts now!");
        });

        finishBarrier = new CyclicBarrier(NUM_RUNNERS, () -> {
            System.out.println("All runners have crossed the finish line.");
        });
    }

    public void startRace() {
        System.out.println("The race has begun!");

        for (int i = 0; i < NUM_RUNNERS; i++) {
            Thread runner = new Thread(() -> {
                System.out.println("Runner " + Thread.currentThread().getName() + " is ready.");
                try {
                    startBarrier.await(); // Wait for all runners to be ready
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }

                // Simulate running
                try {
                    Thread.sleep((long) (Math.random() * 5000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                System.out.println("Runner " + Thread.currentThread().getName() + " has crossed the finish line.");
                try {
                    finishBarrier.await(); // Wait for all runners to cross the finish line
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
            runner.setName(String.valueOf(i));
            runner.start();
        }
    }

    public static void main(String[] args) {
        Race race = new Race();
        race.startRace();
    }
}
