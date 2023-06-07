package com.clay.i_thread_local_storage.examples;

/**
 * In this updated example, the ThreadLocal variables userThreadLocal and requestThreadLocal are initialized using the withInitial() factory method. This method allows us to specify a lambda expression or a method reference that provides the initial value for each thread.
 *
 * By using withInitial(), we set the initial value of userThreadLocal to a User object with the name "DefaultUser" and the initial value of requestThreadLocal to a Request object with the ID "DefaultRequest".
 *
 * The benefit of using withInitial() is that it provides a convenient way to set the initial value for each thread without explicitly calling the set() method.
 * This ensures that each thread starts with its own unique instance of User and Request objects in the UserContext.
 */
class User {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Request {
    private String requestId;

    public Request(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return requestId;
    }
}

class UserContext {
    private static ThreadLocal<User> userThreadLocal = ThreadLocal.withInitial(() -> new User("DefaultUser"));
    private static ThreadLocal<Request> requestThreadLocal = ThreadLocal.withInitial(() -> new Request("DefaultRequest"));

    public static void setUser(User user) {
        userThreadLocal.set(user);
    }

    public static User getUser() {
        return userThreadLocal.get();
    }

    public static void setRequest(Request request) {
        requestThreadLocal.set(request);
    }

    public static Request getRequest() {
        return requestThreadLocal.get();
    }

    public static void clear() {
        userThreadLocal.remove();
        requestThreadLocal.remove();
    }
}

class UserService {
    public void processRequest(String requestId, String userName) {
        User user = new User(userName);
        Request request = new Request(requestId);

        UserContext.setUser(user);
        UserContext.setRequest(request);

        // Perform some operations using the user and request objects
        // ...
        // Simulating some processing time
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Request processed by thread: " + Thread.currentThread().getName());
        System.out.println("User: " + UserContext.getUser().getName());
        System.out.println("Request ID: " + UserContext.getRequest().getRequestId());

        UserContext.clear();
    }
}

public class ThreadLocalComplexExample {
    public static void main(String[] args) {
        UserService userService = new UserService();

        // Create and start multiple threads
        Thread thread1 = new Thread(() -> userService.processRequest("123", "John"));
        Thread thread2 = new Thread(() -> userService.processRequest("456", "Alice"));

        thread1.start();
        thread2.start();
    }
}
