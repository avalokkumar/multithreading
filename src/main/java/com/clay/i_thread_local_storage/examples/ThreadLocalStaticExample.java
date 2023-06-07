package com.clay.i_thread_local_storage.examples;

class UserData {
    private String name;

    public UserData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class UserContextHolder {
    private static final ThreadLocal<UserData> userThreadLocal = new ThreadLocal<>();

    public static void setUser(UserData user) {
        userThreadLocal.set(user);
    }

    public static UserData getUser() {
        return userThreadLocal.get();
    }
}

class UserProcessor {
    public void processRequest(String userName) {
        UserData user = new UserData(userName);
        UserContextHolder.setUser(user);

        System.out.println("Request processed by thread: " + Thread.currentThread().getName());
        System.out.println("User: " + UserContextHolder.getUser().getName());

        // Simulating some processing time
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        UserContextHolder.setUser(null);
    }
}

public class ThreadLocalStaticExample {
    public static void main(String[] args) {
        UserProcessor userProcessor = new UserProcessor();

        Thread thread1 = new Thread(() -> {
            UserContextHolder.setUser(new UserData("Thread 1"));
            userProcessor.processRequest("Thread 1");
        });

        Thread thread2 = new Thread(() -> {
            UserContextHolder.setUser(new UserData("Thread 2"));
            userProcessor.processRequest("Thread 2");
        });

        thread1.start();
        thread2.start();
    }
}
