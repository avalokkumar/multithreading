package com.clay.k_concurrent_collections.CopyOnWriteArraySet;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * In this example, multiple threads will concurrently read and update the set of usernames, and CopyOnWriteArraySet
 * will ensure thread safety without the need for explicit synchronization.
 */
public class UserSetManager {

    // Define CopyOnWriteArraySet to store usernames
    private static final Set<String> userSet = new CopyOnWriteArraySet<>();

    public static void main(String[] args) {
        // Simulate multiple threads concurrently adding and removing usernames
        Runnable userTask = () -> {
            for (int i = 1; i <= 5; i++) {
                String username = "user" + i;
                userSet.add(username);
                System.out.println(Thread.currentThread().getName() + " Added User: " + username);
                try {
                    Thread.sleep(100); // Simulate processing time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 1; i <= 3; i++) {
                String username = "user" + i;
                userSet.remove(username);
                System.out.println(Thread.currentThread().getName() + " Removed User: " + username);
                try {
                    Thread.sleep(100); // Simulate processing time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // Create and start multiple threads to add and remove usernames
        for (int i = 0; i < 3; i++) {
            new Thread(userTask, "Thread-" + (i + 1)).start();
        }

        // Simulate reading usernames concurrently
        Runnable readUsersTask = () -> {
            while (true) {
                System.out.println(Thread.currentThread().getName() + " - Users:");
                for (String user : userSet) {
                    System.out.println(user);
                }
                try {
                    Thread.sleep(1000); // Read usernames every second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // Create and start a thread to read usernames concurrently
        new Thread(readUsersTask, "UserReader").start();
    }
}
