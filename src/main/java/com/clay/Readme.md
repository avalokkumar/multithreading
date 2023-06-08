## Thread Starvation:

Thread starvation occurs when a thread is unable to make progress or complete its task due to a lack of available resources or scheduling issues. 
It can lead to performance degradation and the overall responsiveness of an application. 

### Here are some common scenarios that can cause thread starvation:

1. #### Resource Starvation: 
If a thread requires exclusive access to a shared resource, but other threads are holding the resource for extended periods, the waiting thread may starve. For example, if multiple threads are competing for a lock, and one thread continuously acquires and holds the lock for a long time, other threads waiting for the lock may starve.

2. #### Priority Inversion: 
In a scenario where multiple threads with different priorities are contending for resources, priority inversion can occur. Priority inversion happens when a lower-priority thread holds a resource needed by a higher-priority thread. This can cause the higher-priority thread to wait indefinitely, leading to thread starvation.

3. #### Scheduling Issues: 
In some cases, the thread scheduler may not allocate sufficient CPU time to a particular thread due to scheduling algorithms or system load. This can cause the thread to make slow progress or appear starved.

### To mitigate thread starvation, consider the following approaches:

1. #### Ensure fair resource allocation: 
Use fair locking mechanisms that ensure threads waiting for a resource are given an opportunity to acquire it, preventing indefinite starvation.

2. #### Use thread pooling: 
Employ thread pools and appropriate thread pool configurations to limit the number of concurrent threads and manage resource consumption effectively.

3. #### Optimize resource usage: 
Identify and optimize resource-intensive operations to minimize the time threads spend waiting for resources.

4. #### Review scheduling policies: 
Understand the thread scheduling policies of the underlying platform and consider adjusting thread priorities or scheduling parameters if necessary.

## Deadlock:
Deadlock is a situation in which two or more threads are blocked forever, waiting for each other to release resources that they hold. 

### It occurs when the following four conditions are met:

1. #### Mutual Exclusion: 
At least one resource must be held in a non-sharable mode, meaning it cannot be simultaneously used or accessed by multiple threads.

2. #### Hold and Wait: 
A thread holding at least one resource is waiting to acquire additional resources held by other threads.

3. #### No Preemption: 
Resources cannot be forcibly taken away from threads; they must be released voluntarily.

4. #### Circular Wait: 
There exists a circular chain of two or more threads, each holding a resource that is requested by the next thread in the chain.
When a deadlock occurs, the threads involved will be stuck indefinitely, unable to make any progress. Deadlocks are often subtle and hard to detect, leading to application hangs or freezes.

### To prevent and resolve deadlocks, consider the following strategies:

1. #### Resource Ordering: 
Establish a consistent order in which resources are acquired to prevent circular wait conditions. Threads should always request resources in the same order to avoid deadlock.

2. #### Resource Allocation Strategies: 
Utilize techniques such as resource allocation graphs, bankers' algorithm, or deadlock detection algorithms to manage resource allocation and avoid potential deadlocks.

3. #### Avoid Hold and Wait: 
Implement strategies to ensure that a thread requests all the required resources upfront before execution, rather than holding some resources and then waiting for others.

4. #### Timeouts and Deadlock Detection: 
Set timeouts for resource acquisition, so if a thread is unable to acquire a resource within a specified time, it can release its held resources and retry or take alternative actions. Deadlock detection algorithms can also periodically check for deadlock conditions and take appropriate actions.

5. #### Thread Interruption: 
Use thread interruption mechanisms to break out of potential deadlock situations and handle them gracefully.

6. #### Design Considerations: 
Review and analyze the system design to identify potential deadlock-prone scenarios. Ensure that proper synchronization techniques and resource management practices are followed.