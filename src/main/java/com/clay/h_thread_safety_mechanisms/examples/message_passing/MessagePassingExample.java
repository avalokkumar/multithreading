package com.clay.h_thread_safety_mechanisms.examples.message_passing;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Explanation:
 *
 * In this example, we have a MessagePassingExample class that represents the main program.
 * We create a BlockingQueue named taskQueue to hold the incoming tasks. The LinkedBlockingQueue implementation is used here.
 * We create three WorkerThread objects, passing the taskQueue to each thread in the constructor. The WorkerThread class extends Thread and represents the worker threads responsible for processing tasks.
 * Each worker thread runs in an infinite loop where it takes a task from the taskQueue using the take() method, which blocks if the queue is empty.
 * Once a task is obtained, the worker thread calls the process() method of the Task object to execute the task logic.
 * In the MessagePassingExample class, we enqueue 10 tasks into the taskQueue using the offer() method.
 * The main program then waits for the worker threads to finish processing by calling join() on each worker thread.
 * Each worker thread runs independently, taking tasks from the taskQueue and processing them. The message passing mechanism is achieved by using the blocking queue to pass tasks from the main thread to the worker threads.
 *
 * This approach ensures that the tasks are processed in a thread-safe manner. The worker threads synchronize access to the taskQueue internally, allowing multiple threads to process tasks concurrently without interference.
 *
 * In this example, message passing through the use of a blocking queue enables communication between the main thread and worker threads, facilitating the distribution of tasks among the worker threads in a coordinated and thread-safe manner.
 */
public class MessagePassingExample {
    public static void main(String[] args) {
        // Create a blocking queue to hold the incoming tasks
        BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>();

        // Create worker threads
        WorkerThread worker1 = new WorkerThread(taskQueue);
        WorkerThread worker2 = new WorkerThread(taskQueue);
        WorkerThread worker3 = new WorkerThread(taskQueue);

        // Start the worker threads
        worker1.start();
        worker2.start();
        worker3.start();

        // Enqueue tasks in the task queue
        for (int i = 0; i < 10; i++) {
            Task task = new Task(i);
            taskQueue.offer(task);
        }

        // Wait for the worker threads to finish processing
        try {
            worker1.join();
            worker2.join();
            worker3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}