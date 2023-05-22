package com.clay.f_thread_pools.callable_and_future;

import java.util.Random;
import java.util.concurrent.Callable;

class Task implements Callable<Integer> {
    private int taskId;

    public Task(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public Integer call() throws Exception {
        // Simulating some time-consuming task
        Random random = new Random();
        int sleepTime = random.nextInt(5000) + 1000;
        Thread.sleep(sleepTime);

        // Returning the result
        return taskId * taskId;
    }
}