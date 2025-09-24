## Concurrent Collections:

### Thread-safe collections and Concurrent data structure

#### 1. ConcurrentHashMap:
A highly efficient thread-safe implementation of the `Map` interface designed for concurrent read and write operations. It utilizes lock striping for fine-grained synchronization. By default, it has a concurrency level of 16, an initial capacity of 16, and a load factor of 0.75. This implementation does not allow null keys or values.

#### Key Features:

* Supports concurrent read and thread-safe update operations.
* Achieves high concurrency by employing fine-grained locking at the hashmap bucket level.
* Offers a weakly consistent iterator, ensuring safe traversal without ConcurrentModificationExceptions. However, it doesn't guarantee visibility of elements added post-iterator construction.
* Allows multiple threads to read simultaneously without locking the entire map.
* Suitable for scenarios with high contention for shared resources.

#### 2. CopyOnWriteArrayList
A thread-safe variant of ArrayList in which all mutative operations (e.g., add, set, remove) are implemented by making a fresh copy of the underlying array. This implementation is well-suited for scenarios where traversal operations vastly outnumber mutations.

#### Key Features:

* Thread-Safe Reads: CopyOnWriteArrayList is designed for scenarios where reads are far more frequent than writes. It allows multiple threads to read the list concurrently without the need for explicit synchronization.

* Copy-on-Write Semantics: When an element is added, modified, or removed, a new copy of the underlying array is created. This copy-on-write approach ensures that concurrent reads are not affected by ongoing writes.

* Predictable Iteration: Iterators obtained from a CopyOnWriteArrayList are guaranteed to traverse a snapshot of the list as it existed when the iterator was created. This behavior is particularly useful when consistency in the face of concurrent modifications is essential.

* No ConcurrentModificationException: Unlike many other collections, CopyOnWriteArrayList iterators do not throw ConcurrentModificationException. This means that you can iterate over the list while other threads are modifying it.

* High Contention Reads: CopyOnWriteArrayList is a good choice in situations where there is high contention for read access to a shared list.

* No Null Elements: CopyOnWriteArrayList does not allow null elements. Attempts to add null elements will result in a NullPointerException.

* Higher Write Overhead: Since every write operation creates a new copy of the underlying array, write operations can have a higher overhead compared to other concurrent collections. This makes it less suitable for scenarios with frequent writes.

* Use Cases: CopyOnWriteArrayList is often used in scenarios where read-heavy workloads are prevalent, and data consistency is critical. Examples include maintaining lists of subscribers or listeners in event-driven systems.

* Considerations: While CopyOnWriteArrayList is effective for specific use cases, its performance characteristics make it less suitable for scenarios with frequent write operations or large collections, where the overhead of copying the entire array can become prohibitive.

#### 3. CopyOnWriteArraySet
CopyOnWriteArraySet is a thread-safe variant of Set in which all mutative operations (e.g., add, set, remove) are implemented by making a fresh copy of the underlying array. This implementation is well-suited for scenarios where traversal operations vastly outnumber mutations.

#### Key Features:

* Thread-Safe Reads: CopyOnWriteArraySet is designed for scenarios where reads are far more frequent than writes. It allows multiple threads to read the set concurrently without the need for explicit synchronization.

* Copy-on-Write Semantics: When an element is added or removed, a new copy of the underlying array is created. This copy-on-write approach ensures that concurrent reads are not affected by ongoing writes.

* Predictable Iteration: Iterators obtained from a CopyOnWriteArraySet are guaranteed to traverse a snapshot of the set as it existed when the iterator was created. This behavior is particularly useful when consistency in the face of concurrent modifications is essential.

* No ConcurrentModificationException: Unlike many other collections, CopyOnWriteArraySet iterators do not throw ConcurrentModificationException. This means that you can iterate over the set while other threads are modifying it.

* No Duplicates: CopyOnWriteArraySet does not allow duplicate elements. Attempts to add duplicate elements are ignored.

* No Null Elements: CopyOnWriteArraySet does not allow null elements. Attempts to add null elements will result in a NullPointerException.

* High Contention Reads: CopyOnWriteArraySet is a good choice in situations where there is high contention for read access to a shared set.

* Higher Write Overhead: Since every write operation creates a new copy of the underlying array, write operations can have a higher overhead compared to other concurrent collections. This makes it less suitable for scenarios with frequent writes.

* Use Cases: CopyOnWriteArraySet is often used in scenarios where read-heavy workloads are prevalent, and data consistency is critical. Examples include maintaining a set of active user sessions in a web application or managing a set of subscribers to an event.

* Considerations: While CopyOnWriteArraySet is effective for specific use cases, its performance characteristics make it less suitable for scenarios with frequent write operations or large sets, where the overhead of copying the entire array can become prohibitive.

#### 4. ConcurrentSkipListMap
`ConcurrentSkipListMap` is a thread-safe and concurrent implementation of the NavigableMap interface provided by Java's `java.util.concurrent` package. It is based on a skip list data structure.

#### Key Features:
* Thread-Safe: ConcurrentSkipListMap is designed to support concurrent access from multiple threads. It provides thread safety for both read and write operations without requiring explicit synchronization.

* Sorted Map: It implements the NavigableMap interface, which means it maintains the elements in sorted order based on their natural order or a specified comparator.

* Balanced Data Structure: Underlying ConcurrentSkipListMap is a balanced skip list data structure. This structure ensures efficient search, insertion, and removal operations, even in concurrent scenarios.

* Scalability: ConcurrentSkipListMap is highly scalable, making it suitable for multi-threaded applications where multiple threads can access the map concurrently. It allows concurrent access to keys and values while maintaining balanced data structures.

* Iterator Behavior: Iterators obtained from a ConcurrentSkipListMap are weakly consistent. They do not throw ConcurrentModificationException and guarantee to traverse elements as they existed upon the creation of the iterator. However, they do not guarantee that elements added to the map after the iterator's creation will be visited.

* Search Operations: ConcurrentSkipListMap provides efficient search operations, including get, containsKey, and navigational methods like ceilingKey, floorKey, etc., which work well in concurrent scenarios.

* Concurrent Updates: Multiple threads can safely update the map concurrently. It uses a combination of lock-free and fine-grained locking mechanisms to ensure high concurrency.

* No Null Keys: ConcurrentSkipListMap does not allow null keys. Attempts to add null keys will result in a NullPointerException.

* Use Cases: ConcurrentSkipListMap is suitable for scenarios where you need a thread-safe and sorted map with efficient read and write operations. It is often used in applications where data needs to be maintained in a specific order, such as maintaining leaderboard scores, caching, or indexing.

* Considerations: While ConcurrentSkipListMap provides excellent concurrency and sorting capabilities, it may have slightly higher overhead compared to simpler map implementations, especially in scenarios with low contention and small datasets. It is most beneficial in applications with significant concurrent access requirements and large datasets.


#### 5. ConcurrentSkipListSet:

**Explanation**:
- `ConcurrentSkipListSet` is a concurrent, sorted set implemented using a skip list data structure.
- It is similar to `TreeSet` but offers thread-safe operations without the need for explicit synchronization.
- ConcurrentSkipListSet is sorted according to the natural ordering of its elements or by a Comparator provided at set creation time.

**Key Features**:
- **Thread-Safe**: ConcurrentSkipListSet provides thread-safe access to its elements, allowing multiple threads to access and modify the set concurrently without external synchronization.
- **Sorted**: Elements in ConcurrentSkipListSet are sorted either according to their natural order or by a custom Comparator provided during initialization.
- **High Performance**: It offers logarithmic time complexity for most operations like add, remove, and contains.
- **Scalable**: It scales well with a high degree of concurrency, making it suitable for concurrent applications with heavy contention.
- **Fail-Safe Iterators**: Iterators returned by ConcurrentSkipListSet are weakly consistent and do not throw ConcurrentModificationException even if the set is modified during iteration.

#### 6. BlockingQueue:

**Explanation**:
- BlockingQueue is a queue that supports operations that wait for the queue to become non-empty when retrieving an element and wait for space to become available in the queue when storing an element.
- It is designed to be used in concurrent applications where producers and consumers operate concurrently.

**Key Features**:
- **Thread-Safe**: BlockingQueue implementations provide thread-safe operations for adding, removing, and examining elements.
- **Blocking Operations**: BlockingQueue offers blocking methods like put() and take() which block until space is available in the queue for adding an element or until an element is available for retrieval.
- **Bounded or Unbounded**: Implementations like LinkedBlockingQueue and ArrayBlockingQueue can be either bounded (limited capacity) or unbounded (no fixed capacity).
- **Fairness**: Some implementations, like LinkedBlockingQueue, support optional fairness policies for ordering waiting producer and consumer threads.
- **Support for Producer-Consumer Pattern**: BlockingQueue is commonly used in the producer-consumer pattern, where one set of threads produce data to be consumed by another set of threads.

#### 7. BlockingDeque:

**Explanation**:
- BlockingDeque is a double-ended queue (deque) that supports blocking operations.
- It extends the BlockingQueue interface and adds support for the insertion and removal of elements at both ends.

**Key Features**:
- **Thread-Safe**: Like BlockingQueue, BlockingDeque implementations provide thread-safe operations for adding, removing, and examining elements.
- **Blocking Operations**: BlockingDeque supports blocking operations at both ends, allowing threads to block until space is available for insertion or until an element is available for removal.
- **Dual-End Insertion and Removal**: Elements can be inserted and removed from both the head and the tail of the deque.
- **Bounded or Unbounded**: Similar to BlockingQueue, implementations of BlockingDeque can be either bounded or unbounded.
- **Support for Producer-Consumer Pattern**: BlockingDeque can be used in scenarios where both producers and consumers need to insert and remove elements from both ends of the queue.

#### 8. LinkedBlockingDeque:

**Explanation**:
- LinkedBlockingDeque is an implementation of BlockingDeque with an internal linked structure.
- It maintains two locks, one for the head and one for the tail, allowing concurrent access to both ends of the deque.

**Key Features**:
- **Thread-Safe**: LinkedBlockingDeque provides thread-safe operations for concurrent access by multiple threads.
- **Bounded Capacity**: It can be optionally bounded, meaning it has a maximum capacity, after which attempts to insert new elements will block until space becomes available.
- **FIFO Order**: Elements are inserted and removed in FIFO (First-In-First-Out) order.
- **Blocking Operations**: It supports blocking operations like putFirst(), putLast(), takeFirst(), and takeLast(), which block until the operation can be performed.
- **Scalable**: LinkedBlockingDeque can scale well in applications with a high degree of concurrency, allowing multiple threads to insert and remove elements concurrently.


#### 9. ConcurrentLinkedQueue:

**Explanation**:
- `ConcurrentLinkedQueue` is an unbounded thread-safe queue based on linked nodes.
- It follows the FIFO (First-In-First-Out) order for element retrieval.

**Key Features**:
- **Thread-Safe**: ConcurrentLinkedQueue provides thread-safe operations for concurrent access by multiple threads.
- **Non-Blocking**: Unlike some other concurrent collections, it does not support blocking operations like put() or take(). Instead, it provides non-blocking operations like offer() and poll().
- **High Throughput**: It is suitable for scenarios where high throughput is required and contention is low.
- **Scalable**: ConcurrentLinkedQueue scales well with the number of threads accessing it, making it suitable for highly concurrent applications.

#### 10. ConcurrentLinkedDeque:

**Explanation**:
- `ConcurrentLinkedDeque` is an unbounded thread-safe deque based on linked nodes.
- It allows insertion and removal of elements at both ends.

**Key Features**:
- **Thread-Safe**: ConcurrentLinkedDeque provides thread-safe operations for concurrent access by multiple threads.
- **Dual-End Insertion and Removal**: Similar to BlockingDeque, it supports insertion and removal of elements at both the head and the tail.
- **Non-Blocking Operations**: It offers non-blocking operations for insertion and removal, making it suitable for scenarios where blocking is not desired.
- **Scalable**: ConcurrentLinkedDeque scales well with concurrent access, making it suitable for highly concurrent applications.

#### 11. PriorityBlockingQueue:

**Explanation**:
- `PriorityBlockingQueue` is an unbounded thread-safe priority queue based on a priority heap.
- Elements are ordered according to their natural ordering or by a Comparator provided at queue creation time.

**Key Features**:
- **Thread-Safe**: PriorityBlockingQueue provides thread-safe operations for concurrent access by multiple threads.
- **Priority Ordering**: Elements are ordered according to their priority, either natural ordering or as determined by a Comparator.
- **Blocking Operations**: It supports blocking operations like put() and take(), which block until the operation can be performed.
- **Unbounded Capacity**: PriorityBlockingQueue is unbounded, meaning it can hold an unlimited number of elements.

#### 12. LinkedTransferQueue:

**Explanation**:
- `LinkedTransferQueue` is a thread-safe queue that extends ConcurrentLinkedQueue and provides additional transfer methods.
- It supports both FIFO and LIFO ordering.

**Key Features**:
- **Thread-Safe**: LinkedTransferQueue provides thread-safe operations for concurrent access by multiple threads.
- **Transfer Methods**: It offers additional transfer methods like transfer() and tryTransfer(), which allow threads to wait until another thread takes or adds an element, ensuring handoff coordination.
- **Dual Ordering**: LinkedTransferQueue supports both FIFO and LIFO ordering of elements.
- **Scalable**: It scales well with a high degree of concurrency and is suitable for highly concurrent applications requiring coordinated element transfer.


#### 13. DelayQueue:

**Explanation**:
- `DelayQueue` is an unbounded thread-safe blocking queue where elements are ordered based on their expiration time.
- It is typically used for tasks that need to be executed after a certain delay or at a specific time.

**Key Features**:
- **Thread-Safe**: DelayQueue provides thread-safe operations for concurrent access by multiple threads.
- **Delayed Elements**: Elements in DelayQueue implement the Delayed interface, specifying a time at which they become available for retrieval.
- **Blocking Operations**: It supports blocking operations like take(), which blocks until an element with an expired delay becomes available for retrieval.
- **Unbounded Capacity**: DelayQueue is unbounded, allowing the addition of an unlimited number of delayed elements.

#### 14. ConcurrentMap (an interface):

**Explanation**:
- `ConcurrentMap` is an interface that represents a thread-safe map in Java, providing concurrent access to its key-value pairs.
- It extends the base Map interface and adds atomic operations for put-if-absent, remove-if-equal, and replace operations.

**Key Features**:
- **Thread-Safe**: ConcurrentMap implementations provide thread-safe operations for concurrent access by multiple threads.
- **Atomic Operations**: It supports atomic operations like putIfAbsent(), remove(), and replace(), ensuring that these operations are performed atomically.
- **High Concurrency**: ConcurrentMap implementations are designed to handle high concurrency scenarios efficiently.
- **Scalability**: They scale well with the number of threads accessing the map concurrently.

#### 15. ConcurrentNavigableMap (an interface):

**Explanation**:
- `ConcurrentNavigableMap` is an interface that extends ConcurrentMap and represents a thread-safe navigable map in Java.
- It provides concurrent access to its key-value pairs and supports navigation methods like lowerKey(), floorKey(), ceilingKey(), and higherKey().

**Key Features**:
- **Thread-Safe**: ConcurrentNavigableMap implementations provide thread-safe operations for concurrent access by multiple threads.
- **Navigation Operations**: It supports navigation operations that allow efficient retrieval of elements based on their keys.
- **Scalability**: ConcurrentNavigableMap implementations are designed to handle high concurrency scenarios efficiently.
- **Atomic Operations**: Similar to ConcurrentMap, it supports atomic operations like putIfAbsent(), remove(), and replace().

#### 16. ConcurrentNavigableSet (an interface):

**Explanation**:
- `ConcurrentNavigableSet` is an interface that extends ConcurrentSet and represents a thread-safe navigable set in Java.
- It provides concurrent access to its elements and supports navigation methods like lower(), floor(), ceiling(), and higher().

**Key Features**:
- **Thread-Safe**: ConcurrentNavigableSet implementations provide thread-safe operations for concurrent access by multiple threads.
- **Navigation Operations**: It supports navigation operations that allow efficient retrieval of elements based on their order.
- **Scalability**: ConcurrentNavigableSet implementations are designed to handle high concurrency scenarios efficiently.
- **Atomic Operations**: Similar to ConcurrentMap, it supports atomic operations like add(), remove(), and contains().

#### 17. ConcurrentBag:

**Explanation**:
- `ConcurrentBag` is a thread-safe bag data structure that allows concurrent access to its elements.
- It is typically used when elements need to be added, removed, and examined concurrently without the need for external synchronization.

**Key Features**:
- **Thread-Safe**: ConcurrentBag implementations provide thread-safe operations for concurrent access by multiple threads.
- **Bag Data Structure**: A bag allows adding elements without enforcing uniqueness and supports random access and removal of elements.
- **Concurrent Access**: ConcurrentBag allows concurrent addition, removal, and examination of elements by multiple threads.
- **Scalability**: It scales well with the number of threads accessing the bag concurrently, making it suitable for highly concurrent scenarios.
