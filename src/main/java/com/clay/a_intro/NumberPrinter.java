package com.clay.a_intro;

public class NumberPrinter implements Runnable {
    private final int start;
    private final int end;

    public NumberPrinter(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = start; i <= end; i++) {
            System.out.println("Thread " + Thread.currentThread().getId() + ": " + i);
        }
    }

    public static void main(String[] args) {
        double startTime = System.currentTimeMillis();

        // Create two threads to print numbers 1 to 10 concurrently
        Thread thread1 = new Thread(new NumberPrinter(1, 5));
        Thread thread2 = new Thread(new NumberPrinter(6, 10));

        // Start the threads
        thread1.start();
        thread2.start();

        double endTime = System.currentTimeMillis();

        System.out.println("Execution time: " + (endTime - startTime) + " milliseconds");
    }
}
