package com.clay.i_thread_local_storage.examples;

/**
 * Explanation:
 *
 * * We declare a ThreadLocal variable named threadLocal and initialize it with InheritableThreadLocal. The
 * InheritableThreadLocal class allows values set by the parent thread to be inherited by its child threads.
 *
 * * In the main method, we set the value of threadLocal to "Parent Thread Value".
 *
 * * We define a parentRunnable that represents the code executed by the parent thread.
 *
 * * Within the parentRunnable, we print the value of threadLocal using threadLocal.get(). Since we set the value in the main thread, it should print "Parent Thread Value".
 *
 * * We modify the value of threadLocal to "Modified Parent Thread Value".
 *
 * * We define a childRunnable that represents the code executed by the child thread.
 *
 * * Within the childRunnable, we print the value of threadLocal using threadLocal.get(). Since the child thread inherits the value from its parent, it should print "Modified Parent Thread Value".
 *
 * * We create a new Thread named childThread and start it. The child thread will execute the childRunnable.
 *
 * * We create a new Thread named parentThread and start it. The parent thread will execute the parentRunnable.
 *
 * In this example, we demonstrate that the child thread inherits the value of the ThreadLocal variable from its parent thread.
 * This allows the child thread to access the same value set by the parent thread.
 * This is useful in scenarios where you want to propagate thread-specific data from the parent thread to its child threads,
 * such as passing context or configuration information down the thread hierarchy.
 */
public class InheritedThreadLocalExample {
    private static ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set("Parent Thread Value");

        Runnable parentRunnable = () -> {
            System.out.println("Parent Thread Value: " + threadLocal.get());

            threadLocal.set("Modified Parent Thread Value");

            Runnable childRunnable = () -> {
                System.out.println("Child Thread Value: " + threadLocal.get());
            };

            Thread childThread = new Thread(childRunnable);
            childThread.start();
        };

        Thread parentThread = new Thread(parentRunnable);
        parentThread.start();
    }
}
