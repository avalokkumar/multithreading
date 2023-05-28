package com.clay.g_parallel_processing_parallelism.parallel_data_structure;

import java.util.concurrent.ConcurrentHashMap;

/**
 * In this example, we have a concurrent hash map (ConcurrentHashMap) that is used to store user information. The concurrent hash map is a parallel data structure that allows multiple threads to access and modify its contents concurrently, without the need for explicit synchronization.
 *
 * We simulate multiple threads updating the user information concurrently. Each thread represents a different user and calls the updateUserInformation method to update their respective information in the concurrent hash map. The updateUserInformation method simply puts a new UserInfo object into the map using the user ID as the key.
 *
 * After all the threads have finished updating the user information, we iterate over the entries in the concurrent hash map and print the updated user information.
 *
 * This example showcases the use of a parallel data structure, the concurrent hash map, to efficiently handle concurrent updates to a shared data structure. The concurrent hash map provides thread-safe operations and ensures that updates from different threads do not interfere with each other, allowing for parallelism and improved performance in scenarios where multiple threads need to access and modify the data concurrently.
 */
public class ParallelDataStructureExample {

    public static void main(String[] args) {
        // Create a concurrent hash map to store user information
        ConcurrentHashMap<String, UserInfo> userMap = new ConcurrentHashMap<>();

        // Simulate multiple threads concurrently updating the user information
        Thread thread1 = new Thread(() -> updateUserInformation(userMap, "user1", "John Doe", 25));
        Thread thread2 = new Thread(() -> updateUserInformation(userMap, "user2", "Jane Smith", 30));
        Thread thread3 = new Thread(() -> updateUserInformation(userMap, "user3", "Robert Johnson", 40));

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            // Wait for all threads to finish
            thread1.join();
            thread2.join();
            thread3.join();

            // Print the updated user information
            for (String userId : userMap.keySet()) {
                UserInfo userInfo = userMap.get(userId);
                System.out.println("User ID: " + userId + ", Name: " + userInfo.getName() + ", Age: " + userInfo.getAge());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void updateUserInformation(ConcurrentHashMap<String, UserInfo> userMap, String userId, String name, int age) {
        // Update the user information in the concurrent hash map
        userMap.put(userId, new UserInfo(name, age));
    }

    private static class UserInfo {
        private String name;
        private int age;

        public UserInfo(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}
