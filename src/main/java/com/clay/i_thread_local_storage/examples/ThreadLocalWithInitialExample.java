package com.clay.i_thread_local_storage.examples;

/**
 * In this example, we use the withInitial() factory method to create a ThreadLocal variable with an initial value of "Initial Value". The withInitial() method takes a Supplier lambda expression that provides the initial value.
 *
 * When we access the thread-local variable in the main thread using threadLocalVariable.get(), it will return the initial value "Initial Value". We then modify the thread-local variable using threadLocalVariable.set("Modified Value") in the main thread.
 *
 * Next, we create a child thread and access the thread-local variable within the child thread. The child thread retrieves the initial value of "Initial Value" from the thread-local variable, regardless of the modification made in the main thread. This behavior ensures that each thread has its own independent copy of the thread-local variable.
 *
 * After the child thread finishes, we access the thread-local variable in the main thread again and retrieve the modified value of "Modified Value". This demonstrates that each thread has its own separate instance of the thread-local variable, preserving thread isolation.
 *
 * By using the withInitial() factory method, we can conveniently set the initial value of a ThreadLocal variable without explicitly overriding the initialValue() method.
 */
public class ThreadLocalWithInitialExample {

    // Define a ThreadLocal variable using withInitial() factory method
    private static final ThreadLocal<String> threadLocalVariable =
            ThreadLocal.withInitial(() -> "Initial Value");

    public static void main(String[] args) {
        // Access the thread-local variable in the main thread
        String value = threadLocalVariable.get();
        System.out.println("Main Thread: " + value); // Output: Main Thread: Initial Value

        // Modify the thread-local variable in the main thread
        threadLocalVariable.set("Modified Value");

        // Create a child thread
        Thread childThread = new Thread(() -> {
            // Access the thread-local variable in the child thread
            String childValue = threadLocalVariable.get();
            System.out.println("Child Thread: " + childValue); // Output: Child Thread: Initial Value
        });

        // Start the child thread
        childThread.start();

        // Wait for the child thread to finish
        try {
            childThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Access the thread-local variable in the main thread again
        value = threadLocalVariable.get();
        System.out.println("Main Thread: " + value); // Output: Main Thread: Modified Value
    }
}
