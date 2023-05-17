## Inter-Thread Communication

Inter-thread communication is a mechanism that allows threads to synchronize their actions and exchange information or signals with each other. It provides a way for threads to coordinate their execution and share data in a multi-threaded environment.

In a multi-threaded application, threads often need to cooperate and communicate with each other to accomplish a specific task or avoid race conditions. Inter-thread communication provides a way for threads to signal each other, wait for certain conditions, and transfer data between them.

#### There are several common scenarios where inter-thread communication is used:

* Signaling: Threads need to signal each other to indicate that a certain condition has been met or an event has 
occurred. For example, one thread may need to notify another thread to start processing data after it has finished its own work.

* Waiting: Threads may need to wait for a specific condition to be satisfied before proceeding with their execution. For example, a consumer thread may need to wait for a producer thread to produce data before it can consume it.

* Data Exchange: Threads may need to exchange data with each other. This can be done using shared data structures or dedicated communication channels. For example, one thread may produce data and pass it to another thread for processing.

#### Java provides built-in mechanisms for inter-thread communication, such as:

##### wait(), notify(), and notifyAll():
These methods are defined in the Object class and are used for signaling and waiting. Threads can call wait() to release the lock on an object and wait until another thread calls notify() or notifyAll() on the same object to wake them up.

* ##### wait(): 
The wait() method causes the current thread to release the lock on the object it is called on and enter a state of waiting until another thread calls notify() or notifyAll() on the same object. Here are the key points to understand about wait():

* - When a thread calls wait(), it must own the intrinsic lock (monitor lock) of the object it is called on. Otherwise, 
it will throw an IllegalMonitorStateException.
* - The wait() method is typically used inside a loop that checks for a specific condition. The loop ensures that the thread rechecks the condition after waking up from the wait state to avoid spurious wake-ups.
* - While waiting, the thread is temporarily suspended and does not consume CPU resources.
* - The thread remains in the wait state until it is notified or interrupted by another thread.

* ##### notify(): 
The notify() method wakes up a single thread that is waiting on the object it is called on. If multiple 
threads are waiting, only one thread is chosen to be awakened, and the choice is arbitrary. Here are the key points to understand about notify():

* - When a thread calls notify(), it must own the intrinsic lock (monitor lock) of the object it is called on. Otherwise, it will throw an IllegalMonitorStateException.
* - The awakened thread does not immediately regain the lock. It competes with other threads to acquire the lock again before it can continue execution.
* - The awakened thread transitions from the wait state to the blocked state until it can acquire the lock and continue execution.

* ##### notifyAll(): 
The notifyAll() method wakes up all the threads that are waiting on the object it is called on. Each 
awakened thread transitions from the wait state to the blocked state and competes to acquire the lock. Here are the key points to understand about notifyAll():

* - When a thread calls notifyAll(), it must own the intrinsic lock (monitor lock) of the object it is called on. Otherwise, it will throw an IllegalMonitorStateException.
* - Unlike notify(), notifyAll() wakes up all waiting threads, giving them a chance to proceed.
* - The awakened threads will compete to acquire the lock and continue execution. The exact order in which the threads acquire the lock is not guaranteed.


#### Locks and Conditions: 
The java.util.concurrent.locks package provides advanced mechanisms for inter-thread communication using locks and conditions. 
Locks provide exclusive access to shared resources, and conditions allow threads to wait for specific conditions to be met.

In Java, locks and conditions are advanced mechanisms for inter-thread communication and synchronization. They provide a more flexible and powerful alternative to the traditional wait-notify mechanism. Let's explore locks and conditions in detail:

##### Locks:

* Locks, represented by the Lock interface, provide a way to control the exclusive access to a shared resource by 
multiple threads.
* The key advantage of using locks is their ability to support more fine-grained control over locking and unlocking compared to the intrinsic lock (monitor lock) used with the synchronized keyword.
* Locks offer methods such as lock() to acquire the lock and unlock() to release the lock explicitly.
* Multiple threads can contend for a lock, and only one thread can hold the lock at a given time. Other threads attempting to acquire the lock will be blocked until the lock is released.
* Locks support various features, such as fairness, try-lock, and timed lock acquisition, providing additional flexibility in thread synchronization.

##### Conditions:

* Conditions, represented by the Condition interface, work in conjunction with locks to provide advanced signaling and waiting mechanisms.
* A condition represents a specific condition or state associated with a lock. Threads can wait on a condition until a certain condition is met or a specific event occurs.
* Conditions are created using the newCondition() method on a lock object.
* The await() method is used to make a thread wait on a condition. It releases the associated lock and enters a wait state until another thread signals or interrupts it.
* The signal() method signals one waiting thread on the condition to wake up and continue execution.
* The signalAll() method signals all waiting threads on the condition, giving them an opportunity to proceed.
* When a thread is signaled or interrupted, it needs to re-acquire the lock before it can continue execution.

##### Benefits of Locks and Conditions:

* Locks and conditions provide more flexibility and control over synchronization compared to the traditional wait-notify mechanism.
* They allow multiple conditions to be associated with a single lock, enabling finer-grained synchronization based on different criteria.
* Locks and conditions can be used to build more complex synchronization patterns and algorithms.
* They offer features like fairness, try-lock, and timed wait, which are not available with the intrinsic lock.