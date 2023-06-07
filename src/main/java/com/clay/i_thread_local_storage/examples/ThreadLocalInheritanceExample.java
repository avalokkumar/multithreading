package com.clay.i_thread_local_storage.examples;

import java.util.function.Supplier;

public class ThreadLocalInheritanceExample {

    // CustomThreadLocal extends ThreadLocal to specify the initial value for child threads
    static class CustomThreadLocal<T> extends ThreadLocal<T> {
        private final Supplier<T> initialValueSupplier;

        CustomThreadLocal(Supplier<T> initialValueSupplier) {
            this.initialValueSupplier = initialValueSupplier;
        }

        @Override
        protected T initialValue() {
            // Set the initial value for the main thread
            return initialValueSupplier.get();
        }
    }

    // Create an instance of our CustomThreadLocal with a supplier providing the initial value
    private static final CustomThreadLocal<String> customThreadLocal =
            new CustomThreadLocal<>(() -> "Hello");

    public static void main(String[] args) {
        // Access the thread-local variable in the main thread
        String value = customThreadLocal.get();
        System.out.println("Main Thread: " + value); // Output: Main Thread: Hello

        // Create a child thread
        Thread childThread = new Thread(() -> {
            // Access the thread-local variable in the child thread
            String childValue = customThreadLocal.get();
            System.out.println("Child Thread: " + childValue); // Output: Child Thread: Hello_child
        });

        // Start the child thread
        childThread.start();

        // Wait for the child thread to finish
        try {
            childThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}