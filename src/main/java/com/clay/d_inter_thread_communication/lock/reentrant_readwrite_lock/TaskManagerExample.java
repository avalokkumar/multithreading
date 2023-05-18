package com.clay.d_inter_thread_communication.lock.reentrant_readwrite_lock;

import java.util.List;

public class TaskManagerExample {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        // Create multiple threads to add tasks concurrently
        Thread thread1 = new Thread(() -> taskManager.addTask("Task 1"));
        Thread thread2 = new Thread(() -> taskManager.addTask("Task 2"));

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Retrieve the list of tasks
        List<String> tasks = taskManager.getTasks();
        System.out.println("Tasks: " + tasks);
    }
}
