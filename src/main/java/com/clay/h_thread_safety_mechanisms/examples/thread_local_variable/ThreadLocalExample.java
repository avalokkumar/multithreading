package com.clay.h_thread_safety_mechanisms.examples.thread_local_variable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalExample {
    private static final ThreadLocal<SimpleDateFormat> dateFormatThreadLocal =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("dd-MM-yyyy"));

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // Submit tasks to the thread pool
        executorService.submit(() -> {
            printCurrentDate();
            processTask();
        });

        executorService.submit(() -> {
            printCurrentDate();
            processTask();
        });

        executorService.submit(() -> {
            printCurrentDate();
            processTask();
        });

        executorService.shutdown();
    }

    private static void printCurrentDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = dateFormatThreadLocal.get();
        System.out.println("Current Date: " + dateFormat.format(currentDate));
    }

    private static void processTask() {
        // Perform task logic
        // ...

        // Access thread-local variable
        SimpleDateFormat dateFormat = dateFormatThreadLocal.get();
        // Use the SimpleDateFormat object for formatting dates
        String formattedDate = dateFormat.format(new Date());
        System.out.println("Formatted Date: " + formattedDate);

        // ...
        // Perform other task operations
        // ...
    }
}
