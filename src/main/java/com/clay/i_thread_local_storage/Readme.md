## Thread Local Storage

Thread-local storage (TLS) is a mechanism in programming languages that allows data to be stored and accessed on a per-thread basis. Each thread has its own independent copy of the data, which is not shared with other threads. This provides thread-level isolation and allows variables to have different values for each thread.

Thread-local storage is commonly used in multi-threaded applications where data needs to be accessed and manipulated independently by each thread. It is particularly useful in scenarios where you want to avoid synchronization overhead or prevent data conflicts between threads.

In Java, the ThreadLocal class is used to implement thread-local storage. It provides a simple and efficient way to associate values with threads. 

---
### Here are the key components and usage patterns of ThreadLocal:

#### 1. ThreadLocal Class: 
The ThreadLocal class provides methods to create and manage thread-local variables. It maintains a separate copy of the variable for each thread. The class provides methods like get(), set(), and remove() to access and manipulate the thread-local value.

#### 2. Creating ThreadLocal Variables: 
To create a thread-local variable, you can declare an instance of ThreadLocal with the desired type and initialize it. 
For example:
```java
ThreadLocal<Integer> threadLocalVariable = new ThreadLocal<>();
```

#### 3. Setting and Getting Values: 
Each thread can set and get its own value for a thread-local variable using the set() and get() methods. 
For example:
```java
threadLocalVariable.set(42); // Set value for current thread
int value = threadLocalVariable.get(); // Get value for current thread
```

#### 4. Initializing ThreadLocal Values: 
You can provide an initial value for the thread-local variable by overriding the initialValue() method of ThreadLocal. This method is called the first time get() is invoked on the variable for a thread that doesn't have a value set yet.

#### 5. ThreadLocal with Inheritance: 
By default, a child thread inherits the thread-local values from its parent thread. However, you can override the childValue() method of ThreadLocal to customize the initial value for child threads.

#### 6. Cleaning up ThreadLocal Values: 
It's important to clean up thread-local values after their use to prevent memory leaks. You can do this by invoking the remove() method on the thread-local variable, typically in a finally block.

---
### Thread-local storage is used in various scenarios, such as:

#### 1. Storing per-thread state: 
Thread-local variables are used to store state that is specific to each thread, such as thread-specific configurations, session data, or context information.

#### 2. Avoiding synchronization: 
By using thread-local variables, you can avoid synchronization mechanisms like locks or atomic operations when working with thread-specific data. This can improve performance by reducing contention between threads.

#### 3. Contextual information: 
In web applications, thread-local storage is often used to store request-specific or user-specific information. This allows different threads to process different requests independently.

#### 4.  Debugging and tracing: 
Thread-local variables can be used to attach additional debugging or tracing information to each thread, helping to identify and track issues in multi-threaded applications.

---

### Different ways to use ThreadLocal for managing thread-local variables and data

#### 1. Directly using ThreadLocal class: 
You can create an instance of the ThreadLocal class and use its methods to set and get values for each thread. This is the most common and straightforward approach.

Example:
```java
ThreadLocal<Integer> threadLocalVariable = new ThreadLocal<>();
threadLocalVariable.set(42); // Set value for current thread
int value = threadLocalVariable.get(); // Get value for current thread
```

#### 2. Inheriting ThreadLocal values: 
By default, child threads inherit the thread-local values from their parent thread. You can override the `childValue()` method of `ThreadLocal` to customize the initial value for child threads.

Example:
```java
ThreadLocal<Integer> threadLocalVariable = new ThreadLocal<>() {
    @Override
    protected Integer initialValue() {
        return 0; // Initial value for the thread-local variable
    }

    @Override
    protected Integer childValue(Integer parentValue) {
        return parentValue + 1; // Custom value for child threads
    }
};
threadLocalVariable.set(42); // Set value for current thread
int value = threadLocalVariable.get(); // Get value for current thread
```

#### 3. Using withInitial() factory method: 
The `ThreadLocal` class provides a `withInitial()` factory method that allows you to specify an initial value using a Supplier lambda expression. This is useful when you want to lazily initialize the thread-local variable.

Example:
```java
ThreadLocal<Integer> threadLocalVariable = ThreadLocal.withInitial(() -> 42); // Initial value using Supplier
int value = threadLocalVariable.get(); // Get value for current thread
```

#### 4. Using ThreadLocal as a static variable:
You can declare a `ThreadLocal` variable as `static` and share it across multiple threads. Each thread will have its own independent copy of the variable.

Example:
```java
public class MyThreadLocalClass {
    private static ThreadLocal<Integer> threadLocalVariable = new ThreadLocal<>();

    public static void setValue(int value) {
        threadLocalVariable.set(value); // Set value for current thread
    }

    public static int getValue() {
        return threadLocalVariable.get(); // Get value for current thread
    }
}
```