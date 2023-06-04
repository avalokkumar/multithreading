package com.clay.h_thread_safety_mechanisms.examples.message_passing;

class Task {
    private int taskId;

    public Task(int taskId) {
        this.taskId = taskId;
    }

    public void process() {
        System.out.println("Processing Task ID: " + taskId);
        // Perform the task logic
        // ...
    }
}