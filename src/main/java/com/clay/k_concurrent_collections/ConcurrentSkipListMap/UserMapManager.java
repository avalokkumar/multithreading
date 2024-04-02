package com.clay.k_concurrent_collections.ConcurrentSkipListMap;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Here, ConcurrentSkipListMap is used to maintain a map of user IDs and their corresponding names in a messaging application.
 * In this example, multiple threads will concurrently read and update the map of user information,
 * and ConcurrentSkipListMap will ensure thread safety without the need for explicit synchronization.
 */
public class UserMapManager {

    // Define ConcurrentSkipListMap to store user information
    private static Map<Integer, String> userMap = new ConcurrentSkipListMap<>();

    public static void main(String[] args) {
        // Simulate multiple threads concurrently adding and removing user information
        Runnable userTask = () -> {
            for (int i = 1; i <= 5; i++) {
                userMap.put(i, "User" + i);
                System.out.println(Thread.currentThread().getName() + " Added User: User" + i);
                try {
                    Thread.sleep(100); // Simulate processing time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // Remove user information
            for (int i = 1; i <= 3; i++) {
                userMap.remove(i);
                System.out.println(Thread.currentThread().getName() + " Removed User: User" + i);
                try {
                    Thread.sleep(100); // Simulate processing time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // Create and start multiple threads to add and remove user information
        for (int i = 0; i < 3; i++) {
            new Thread(userTask, "Thread-" + (i + 1)).start();
        }

        // Simulate reading user information concurrently
        Runnable readUsersTask = () -> {
            while (true) {
                System.out.println(Thread.currentThread().getName() + " - Users:");
                for (Map.Entry<Integer, String> entry : userMap.entrySet()) {
                    System.out.println("User ID: " + entry.getKey() + ", Name: " + entry.getValue());
                }
                try {
                    Thread.sleep(1000); // Read user information every second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // Create and start a thread to read user information concurrently
        new Thread(readUsersTask, "UserReader").start();
    }
}
