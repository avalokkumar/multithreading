package com.clay.h_thread_safety_mechanisms.examples.software_transactional_memory;

import java.util.concurrent.atomic.AtomicInteger;

class Transaction {
    private final AtomicInteger status = new AtomicInteger(0); // 0 = In progress, 1 = Committed, -1 = Aborted

    public void run(Runnable task) {
        if (status.compareAndSet(0, 1)) { // Check if transaction is in progress
            task.run();
            status.set(1); // Commit the transaction
        } else {
            status.set(-1); // Abort the transaction
        }
    }
}