# Thread pools
Thread pools are a concept in concurrent programming that involve managing a pool of worker threads to execute tasks efficiently. A thread pool consists of a group of pre-initialized worker threads that are ready to perform tasks as soon as they become available. It provides a way to reuse threads rather than creating new ones for each task, which can be expensive and inefficient.

## Introduction to thread pools.

### Here's an overview of how thread pools work:

1. #### Initialization: 
When creating a thread pool, you specify the initial number of threads to be created. These threads are created in an idle state and are ready to execute tasks.

2. #### Task Submission: 
To execute a task in a thread pool, you submit the task to the pool. The pool maintains a task queue where submitted tasks are stored until a worker thread becomes available to execute them.

3. #### Task Execution: 
The worker threads in the pool continuously check the task queue for pending tasks. When a task becomes available, an idle thread is assigned to execute it. Once the task is completed, the thread becomes available again to pick up the next task from the queue.

4. #### Thread Reuse: 
Thread pools facilitate thread reuse, eliminating the overhead of thread creation for each task. Instead of creating and destroying threads, the pool reassigns existing threads to execute different tasks, improving performance and resource utilization.

5. #### Thread Lifecycle Management: 
Thread pools handle thread lifecycle management, including thread creation, termination, and exception handling. They provide a higher-level abstraction for managing threads, reducing the complexity of manual thread management.


### Example
```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExample {
    public static void main(String[] args) {
        // Step 1: Initialize the thread pool
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Step 2: Task submission
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            executor.submit(() -> {
                // Step 3: Task execution
                System.out.println("Task " + taskId + " executed by " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000); // Simulating some work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Task " + taskId + " interrupted");
                }
                System.out.println("Task " + taskId + " completed");
            });
        }

        // Step 4: Shutdown the thread pool
        executor.shutdown();
    }
}
```

#### Explanation:

* **Initialization:** We create a fixed-size thread pool with three threads using `Executors.newFixedThreadPool(3)`. 
  This initializes a thread pool with three worker threads ready to execute tasks.

* **Task Submission:** We submit five tasks to the thread pool using the `submit()` method. Each task is a lambda expression representing a `Runnable` that prints the task ID, the executing thread's name, performs some work (simulated by a `Thread.sleep()`), and prints completion status.

* **Task Execution:** The thread pool assigns the submitted tasks to the available worker threads. Since we have three threads in the pool, the first three tasks start executing immediately, each on a separate thread. The remaining tasks wait for an available thread.

* **Thread Reuse:** Once a worker thread completes a task, it is returned to the thread pool and can be immediately assigned to the next available task. This allows for efficient thread reuse, eliminating the overhead of creating and destroying threads for each task.

* **Thread Lifecycle Management:** The thread pool manages the lifecycle of the worker threads. When a task is submitted, the thread pool either assigns it to an available thread or enqueues it until a thread becomes available. After all tasks are executed, we call `executor.shutdown()` to initiate a graceful shutdown of the thread pool. This allows the existing tasks to complete while preventing new tasks from being accepted.

---

### Advantages of Thread Pools:

1. #### Improved Performance: 
Thread pools allow for efficient utilization of system resources by reusing threads, reducing overhead and latency associated with thread creation.

2. #### Thread Management: 
Thread pools abstract away the low-level details of thread creation, termination, and exception handling, making thread management easier and less error-prone.

3. #### Control Over Resource Usage: 
Thread pools provide control over the number of threads in the pool, preventing resource exhaustion in situations where tasks are submitted at a high rate.

4. #### Load Balancing:
Thread pools distribute tasks among available threads, ensuring a balanced workload and maximizing throughput.

5. #### Scalability:
Thread pools can be scaled up or down dynamically, adjusting the number of threads based on the workload, system resources, and application requirements.
Java provides a built-in thread pool implementation called ExecutorService, which is part of the java.util.concurrent package. It offers various implementations, such as ThreadPoolExecutor and ScheduledThreadPoolExecutor, to create and manage thread pools.

---

#### Here's an example of how to create and use a thread pool with ExecutorService:

In this example, we create a fixed-size thread pool with 5 worker threads using `Executors.newFixedThreadPool(5)`. We then submit 10 tasks to the thread pool using the `submit()` method of the `ExecutorService`. 
Each task is an instance of the `Task` class, which implements the `Runnable` interface.

The thread pool automatically assigns available threads to execute the submitted tasks concurrently. 
The `run()` method of the `Task` class contains the logic to be executed by each worker thread. 
Once all tasks are submitted, we call `executorService.shutdown()` to initiate a graceful shutdown of the thread pool.

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExample {
    public static void main(String[] args) {
        // Create a fixed-size thread pool with 5 worker threads
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // Submit tasks to the thread pool
        for (int i = 0; i < 10; i++) {
            executorService.submit(new Task(i));
        }

        // Shutdown the thread pool
        executorService.shutdown();
    }
}

class Task implements Runnable {
    private int taskId;

    public Task(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        System.out.println("Task " + taskId + " is being executed by " + Thread.currentThread().getName());
        // Task logic goes here
    }
}
```
---
---

## Executor framework in Java

The Executor framework in Java provides a higher-level abstraction for managing and executing tasks asynchronously. 
It decouples the task submission and execution, allowing developers to focus on the logic of the tasks rather than the low-level details of thread management. 
The Executor framework is part of the `java.util.concurrent` package and offers several interfaces and classes to work with.

1. #### Executor Interface:
The `Executor` interface represents an object that executes submitted tasks. It defines a single method execute(Runnable command) 
that takes a `Runnable` task and executes it asynchronously. Implementations of the `Executor` interface can execute tasks immediately, 
using a new thread for each task, or enqueue them for execution based on the implementation.

2. #### ExecutorService Interface:
The `ExecutorService` interface extends the `Executor` interface and provides additional methods to manage and control the execution of tasks. 
It represents an asynchronous execution service that manages a pool of threads and provides features like task submission, 
task completion, and thread management. Some important methods of `ExecutorService` are:

* - `submit`(`Runnable` task): Submits a `Runnable` task for execution and returns a `Future` representing the task's result.
* - `submit`(`Callable<T>` task): Submits a `Callable` task for execution and returns a `Future` representing the task's result.
* - `shutdown()`: Initiates a graceful shutdown of the `ExecutorService`, allowing previously submitted tasks to complete.
* - `shutdownNow()`: Attempts to stop the execution of the tasks and terminates the `ExecutorService` immediately.
* - `awaitTermination(long timeout, TimeUnit unit)`: Blocks until all tasks have completed execution after a shutdown request, or the timeout occurs.

3. #### Executors Class:
The `Executors` class provides factory methods to create instances of thread pools and other types of `ExecutorService` implementations. 
It offers different types of thread pools with various characteristics and behaviors:

* - `newFixedThreadPool(int nThreads)`: Creates a fixed-size thread pool with a specified number of threads.
* - `newCachedThreadPool()`: Creates a thread pool that creates new threads as needed but reuses idle threads when available.
* - `newSingleThreadExecutor()`: Creates a single-threaded executor that guarantees the sequential execution of tasks.
* - `newScheduledThreadPool(int corePoolSize)`: Creates a thread pool that can schedule tasks to run after a delay or periodically.

4. #### Callable and Future:
The `Callable` interface is similar to `Runnable`, but it can return a result and throw checked exceptions. 
It represents a task that returns a value upon completion. The `Future` interface represents the result of an asynchronous computation. 
It provides methods to check the status of a `task`, cancel the task, and retrieve the result (blocking if necessary) once the task is complete.

### When to Use the Executor Framework:

1. #### Asynchronous Task Execution: 
When you need to execute tasks asynchronously without blocking the main thread or waiting for the task completion.

2. #### Concurrent Processing:
When you have a set of tasks that can be executed concurrently to improve performance, such as processing multiple files or performing parallel computations.

3. #### Task Management: 
When you want to manage the lifecycle of tasks, including submission, cancellation, and retrieval of results.

4. #### Thread Pool Management: 
When you need to control the number of threads and efficiently reuse them for executing multiple tasks, preventing the overhead of thread creation for each task.

### How to Use the Executor Framework:

* Create an instance of `ExecutorService` using the factory methods from the `Executors` class.
* Define your tasks by implementing the Runnable or `Callable` interface.
* Submit tasks to the `ExecutorService` using the `submit()` method.
* Optionally, obtain a `Future` object representing the task's result if you need to retrieve the result or cancel the task.
* After submitting all tasks, call the `shutdown()` method to initiate a graceful shutdown of the `ExecutorService`.
* Handle the task results using the `Future` objects if necessary.