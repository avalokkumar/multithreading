package com.clay.h_thread_safety_mechanisms.examples.message_passing;

import java.util.concurrent.BlockingQueue;

class WorkerThread extends Thread {
    private final BlockingQueue<Task> taskQueue;

    public WorkerThread(BlockingQueue<Task> taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Take a task from the task queue
                Task task = taskQueue.take();
                // Process the task
                task.process();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}