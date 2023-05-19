## Thread Coordination
Thread Coordination is the process of controlling the execution and synchronization of multiple threads to achieve desired behavior and ensure proper communication between them. It involves techniques such as joining threads, thread interrupts, interruption handling, thread timeouts, and timed waiting. Let's explore each of these concepts in detail:

1. ### Joining Threads:
Joining threads is a mechanism that allows one thread to wait for the completion of another thread before proceeding further. When a thread calls the join() method on another thread, it waits until that thread completes its execution. Joining threads is useful when you want to ensure that certain tasks are completed before proceeding to the next step. 
Here's an example:

```java
Thread thread1 = new Thread(() -> {
    // Task 1
});
Thread thread2 = new Thread(() -> {
    // Task 2
});

thread1.start();
thread2.start();

try {
    thread1.join();
    thread2.join();
} catch (InterruptedException e) {
    e.printStackTrace();
}

// Proceed with the next steps after both threads have completed
```

#### What is Joining Threads?
Joining threads allows you to ensure that certain tasks or operations are completed by one thread before other threads can proceed. It provides a way to synchronize the execution of multiple threads and coordinate their actions. By joining a thread, you effectively pause the execution of the current thread until the joined thread finishes its execution.

#### When do we use Joining Threads?
Joining threads is useful in various scenarios, including:

* ##### Coordinating parallel tasks: 
When you have multiple threads performing different tasks in parallel, you may want to 
wait for all threads to finish their respective tasks before proceeding with the next steps.

* ##### Collecting results: 
If each thread is performing a specific computation or operation and you need the combined result of all threads, you can join all the threads and collect their results.

* ##### Waiting for thread completion: 
In some cases, you may have a main thread or a controlling thread that needs to wait for specific worker threads to complete their work before terminating the application or performing subsequent actions.

#### How do we use Joining Threads?
To join a thread, you follow these steps:

* ##### Create the threads: 
Create the threads you want to join. You can create them by extending the Thread class or implementing the Runnable interface.

* ##### Start the threads: 
Invoke the start() method on each thread to start their execution.

* ##### Join the threads: 
Use the join() method to join the threads. The calling thread will pause and wait for the joined thread to complete.

* ##### Handle exceptions: 
When joining threads, it's important to handle the InterruptedException that may be thrown. This exception is typically thrown if the thread is interrupted while waiting to join. You can handle the exception appropriately based on your application's requirements.

> Here's a complete example that demonstrates joining threads:

```java
public class JoiningThreadsExample {
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            System.out.println("Thread 1 started");
            // Perform some task
            System.out.println("Thread 1 completed");
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("Thread 2 started");
            // Perform some task
            System.out.println("Thread 2 completed");
        });

        thread1.start();
        thread2.start();

        try {
            // Join both threads
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // All threads have completed
        System.out.println("All threads completed");
    }
}
```

---

2. ### Thread Interrupts and Interruption Handling:
Thread interrupts provide a way to request the interruption of a thread's execution. When a thread is interrupted using the interrupt() method, it sets the interrupted status of the thread, which can be checked using the isInterrupted() method. Interruption is a cooperative mechanism, where the interrupted thread needs to check its interrupted status periodically and decide how to handle it. 

Here's an example:
```java
Thread thread = new Thread(() -> {
    while (!Thread.currentThread().isInterrupted()) {
        // Perform some task
        try {
            Thread.sleep(1000); // Handle interruption while sleeping
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Preserve the interrupted status
            // Custom interruption handling logic
        }
    }
});

thread.start();

// Request the interruption of the thread
thread.interrupt();
```

#### What are Thread Interrupts?
A thread interrupt is a way to request the interruption or termination of a thread's execution by another thread. It is a cooperative mechanism where the interrupted thread is responsible for handling the interruption request and deciding how to respond to it.

#### When do we use Thread Interrupts?
Thread interrupts are useful in various scenarios, including:

* ##### Cooperative thread termination: 
If you want to gracefully terminate a long-running or blocking thread from another thread, you can use interrupts to request the thread to stop its execution.

* ##### Thread coordination: 
Interrupts can be used to coordinate the actions of multiple threads. For example, one thread can interrupt another thread to signal a specific condition or event.

* ##### Responsive application behavior: 
Interrupts can be used to make your application more responsive by allowing threads to be interrupted while waiting for a resource or performing a task.

#### How do we use Thread Interrupts?
To interrupt a thread, you follow these steps:

* ##### Call the interrupt() method: 
Invoke the interrupt() method on the target thread to request its interruption. This sets the interrupt status of the thread.

* ##### Check the interrupt status: 
The interrupted thread should periodically check its interrupt status using the isInterrupted() method to determine if an interrupt request has been received.

* ##### Handle the interruption: 
Based on the interrupt status, the thread should handle the interruption appropriately. This may involve cleaning up resources, terminating the thread's execution, or taking other necessary actions.

Here's a complete example that demonstrates thread interrupts and interruption handling:
```java
public class ThreadInterruptExample {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                // Perform some task
                try {
                    Thread.sleep(1000); // Simulate some work
                } catch (InterruptedException e) {
                    // Handle the interruption
                    Thread.currentThread().interrupt(); // Restore the interrupted status
                }
            }
        });

        thread.start();

        // Request the interruption after a delay
        try {
            Thread.sleep(5000);
            thread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```
In the above example, we create a thread that performs a task inside a loop. The thread periodically checks its 
interrupt status using `isInterrupted()`. If an interrupt request is received, it handles the interruption by restoring the interrupted status with `Thread.currentThread().interrupt()`. This allows the thread to exit the loop and gracefully terminate its execution.

In the main method, we request the interruption of the thread after a delay by calling `thread.interrupt()`. This triggers the interruption handling logic in the thread.

---

3. ### Thread Timeouts and Timed Waiting:
Thread timeouts and timed waiting refer to the ability to wait for a certain period of time for a specific condition 
to be met or an action to be completed. This can be achieved using methods such as wait(long timeout), join(long 
millis), wait(long timeout), sleep(long millis), or Lock.tryLock(long timeout). By specifying a timeout value, a thread can wait for a specified duration and then proceed with the next steps if the condition is not met within that time.

Here's an example:

```java
Object lock = new Object();

synchronized (lock) {
    try {
        // Wait for a specific condition with a timeout of 5 seconds
        lock.wait(5000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
```
In the example above, the thread waits for a notification on the lock object for a maximum of 5 seconds. If no notification is received within that time, the thread resumes execution and continues with the next steps.

### Thread Timeouts
Thread timeouts refer to setting a maximum time duration for a thread to wait for a specific condition or event to occur. It allows you to limit the waiting time and proceed with alternative actions if the condition is not met within the specified time.

Here's a more detailed explanation of thread timeouts:

#### What is it?

Thread timeouts enable you to specify a duration for which a thread should wait for a particular condition or event before timing out.
When a thread encounters a timeout, it stops waiting and can perform alternative actions instead of waiting indefinitely.

#### When do we use it?

Thread timeouts are used in situations where waiting indefinitely for a condition or event to happen is not desirable or practical.
It is commonly used when dealing with external resources or operations that might experience delays or become unresponsive.
Timeouts help prevent threads from blocking indefinitely and allow you to handle situations when waiting for too long is not an option.

#### How do we use it?

* The wait(long timeout) method provided by the Object class is commonly used to implement thread timeouts.
* Here's an example of how you can use thread timeouts:
```java
synchronized (lock) {
    try {
        lock.wait(5000); // Wait for 5 seconds or until notified
    } catch (InterruptedException e) {
        // Handle interruption if needed
        e.printStackTrace();
    }
}
```
* In this example, the thread acquires the lock and waits for a maximum of 5 seconds. If it is not notified within that time or if the thread is interrupted, it will continue execution.
* It's important to note that the timeout value passed to the `wait()` method is in milliseconds.

#### Handling timeouts:

* When using thread timeouts, it's essential to handle the InterruptedException that can occur if the waiting thread 
gets interrupted before the timeout duration elapses.
* Interrupting a thread can happen when another thread invokes the `interrupt()` method on it.
* Proper handling of `InterruptedException` allows you to take appropriate actions when timeouts are triggered or interruptions occur.
* You might choose to stop the execution, clean up resources, or take any other necessary action based on the application's requirements.


### Timed Waiting

Timed waiting refers to a state in which a thread suspends its execution for a specified period of time. It allows the thread to wait for a certain duration before resuming its activity. Timed waiting is useful in scenarios where you need to introduce delays or schedule actions based on time intervals.

Here's a more detailed explanation of timed waiting:

#### What is it?

* Timed waiting is a state in which a thread temporarily stops its execution for a specific period of time.
* The thread enters a wait state and remains idle until the specified time elapses or until it is interrupted.

#### When do we use it?

* Timed waiting is used in various scenarios, including:
* - Implementing timeouts: You can use timed waiting to introduce delays and enforce time limits for certain 
  operations. For example, if a resource is not available within a specific time, you can interrupt or take alternative actions.
* - Scheduling recurring tasks: Timed waiting is useful when you need to schedule tasks to run repeatedly after specific time intervals. It allows you to introduce delays between subsequent executions.
* - Rate limiting: In some cases, you might want to limit the rate at which certain actions or operations occur. Timed waiting can be used to introduce delays between consecutive invocations.

#### How do we use it?

* In Java, the `Thread.sleep()` method is commonly used to implement timed waiting.
* Here's an example of how you can use timed waiting to introduce a delay:
```java
try {
Thread.sleep(2000); // Sleep for 2 seconds
} catch (InterruptedException e) {
// Handle interruption if needed
e.printStackTrace();
}
```
* In this example, the current thread will be paused for approximately 2 seconds.
* It's important to note that the parameter passed to `Thread.sleep()` is the duration in milliseconds.

#### Interrupting timed waiting:

* During timed waiting, if another thread interrupts the waiting thread by invoking its `interrupt()` method, the waiting thread will be awakened prematurely, and an `InterruptedException` will be thrown.
* It's important to handle `InterruptedException` appropriately to handle interruptions and take necessary actions based on the application's requirements.