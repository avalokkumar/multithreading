package com.clay.k_concurrent_collections.ConcurrentHashMap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * cache for storing user information, where user IDs serve as keys and user details (name, age, email) serve as values.
 * Multiple threads will concurrently access and modify the cache, and ConcurrentHashMap will ensure thread safety without
 * the need for explicit synchronization.
 */
public class UserCacheExample {

    //ConcurrentHashMap to store user information
    private static Map<Integer, UserInfo> userCache = new ConcurrentHashMap<>();

    static class UserInfo {
        private String name;
        private int age;
        private String email;

        public UserInfo(String name, int age, String email) {
            this.name = name;
            this.age = age;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public static void main(String[] args) {

        userCache.put(1, new UserInfo("Alice", 30, "alice@example.com"));
        userCache.put(2, new UserInfo("Bob", 35, "bob@example.com"));
        userCache.put(3, new UserInfo("Charlie", 25, "charlie@example.com"));

        // Simulating concurrent access by multiple threads
        Runnable readTask = () -> {
            for (int i = 0; i < 5; i++) {
                // Read user details from the cache
                int userId = (int) (Math.random() * 3) + 1; // Generate random user ID
                UserInfo user = userCache.get(userId);
                System.out.println(Thread.currentThread().getName() + " Read User: " + userId + " - " + user.getName());
                try {
                    Thread.sleep(100); // Simulating processing time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable writeTask = () -> {
            for (int i = 0; i < 5; i++) {
                // Update user details in the cache
                int userId = (int) (Math.random() * 3) + 1;
                UserInfo user = userCache.get(userId);
                user.setAge(user.getAge() + 1);
                System.out.println(Thread.currentThread().getName() + " Updated Age for User: " + userId + " to " + user.getAge());
                try {
                    Thread.sleep(100); // Simulating processing time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // Create and start multiple reader and writer threads
        for (int i = 0; i < 3; i++) {
            new Thread(readTask, "Reader-" + (i + 1)).start();
            new Thread(writeTask, "Writer-" + (i + 1)).start();
        }
    }
}
