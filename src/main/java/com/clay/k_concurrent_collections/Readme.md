## Concurrent Collections:

### Thread-safe collections and Concurrent data structure

#### 1. ConcurrentHashMap:
A highly efficient thread-safe implementation of the Map interface designed for concurrent read and write operations. It utilizes lock striping for fine-grained synchronization. By default, it has a concurrency level of 16, an initial capacity of 16, and a load factor of 0.75. This implementation does not allow null keys or values.

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

#### 5. ConcurrentSkipListSet
#### 6. BlockingQueue (including implementations like LinkedBlockingQueue and ArrayBlockingQueue)
#### 7. BlockingDeque
#### 8. LinkedBlockingDeque
#### 9. ConcurrentLinkedQueue
#### 10. ConcurrentLinkedDeque
#### 11. PriorityBlockingQueue
#### 12. LinkedTransferQueue
#### 13. DelayQueue
#### 14. ConcurrentMap (an interface)
#### 15. ConcurrentNavigableMap (an interface)
#### 16. ConcurrentNavigableSet (an interface)
#### 17. ConcurrentBag