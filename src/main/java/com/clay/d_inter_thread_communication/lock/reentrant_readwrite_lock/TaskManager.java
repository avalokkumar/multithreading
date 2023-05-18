package com.clay.d_inter_thread_communication.lock.reentrant_readwrite_lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * TaskManager represents a simple task management system that allows multiple threads
 * to add tasks concurrently while ensuring exclusive access when modifying the task list.
 */
public class TaskManager {
    private List<String> tasks;
    private ReentrantReadWriteLock lock;

    /**
     * Constructs a TaskManager with an empty task list.
     */
    public TaskManager() {
        tasks = new ArrayList<>();
        lock = new ReentrantReadWriteLock();
    }

    /**
     * Adds a new task to the task list.
     *
     * @param task the task to be added
     */
    public void addTask(String task) {
        lock.writeLock().lock(); // Acquire the write lock
        try {
            tasks.add(task);
            System.out.println("Task added: " + task);
        } finally {
            lock.writeLock().unlock(); // Release the write lock
        }
    }

    /**
     * Retrieves the list of tasks.
     *
     * @return the list of tasks
     */
    public List<String> getTasks() {
        lock.readLock().lock(); // Acquire the read lock
        try {
            return new ArrayList<>(tasks);
        } finally {
            lock.readLock().unlock(); // Release the read lock
        }
    }
}
