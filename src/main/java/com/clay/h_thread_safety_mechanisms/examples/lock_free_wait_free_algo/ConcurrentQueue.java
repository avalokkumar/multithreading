package com.clay.h_thread_safety_mechanisms.examples.lock_free_wait_free_algo;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Explanation:
 *
 * We define the ConcurrentQueue class, which represents a concurrent queue. It internally uses the AtomicReference class to maintain atomic references to the head and tail nodes of the queue.
 *
 * We define a nested class Node to represent individual nodes in the queue. Each node contains a value of type T and an atomic reference to the next node in the queue.
 *
 * The constructor initializes the head and tail references to a dummy node. This simplifies the implementation by avoiding null checks.
 *
 * The enqueue() method is used to add an item to the queue atomically. It creates a new node and attempts to update the tail reference atomically using a loop. It compares the current tail with the expected value and updates it only if it matches. If the update is successful, the method returns. If another thread has updated the tail in the meantime, it retries the operation.
 *
 * The dequeue() method is used to remove and return an item from the queue atomically. It operates in a similar manner to the enqueue() method, but it updates the head reference instead. It handles cases where the queue is empty or contains only one item.
 *
 * This lock-free algorithm allows multiple threads to enqueue and dequeue items from the queue concurrently without requiring explicit locks. It achieves thread-safety and avoids blocking or waiting, improving overall performance and scalability in highly concurrent scenarios.
 *
 * @param <T>
 */
import java.util.concurrent.atomic.AtomicReference;

public class ConcurrentQueue<T> {
    private static class Node<T> {
        final T value;
        AtomicReference<Node<T>> next;

        Node(T value) {
            this.value = value;
            next = new AtomicReference<>(null);
        }
    }

    private AtomicReference<Node<T>> head;
    private AtomicReference<Node<T>> tail;

    public ConcurrentQueue() {
        Node<T> dummy = new Node<>(null);
        head = new AtomicReference<>(dummy);
        tail = new AtomicReference<>(dummy);
    }

    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);
        while (true) {
            Node<T> currentTail = tail.get();
            Node<T> tailNext = currentTail.next.get();
            if (currentTail == tail.get()) { // Check if tail has not changed
                if (tailNext == null) { // No other enqueuer is in progress
                    if (currentTail.next.compareAndSet(null, newNode)) { // Try to link the new node at the tail
                        tail.compareAndSet(currentTail, newNode); // Move the tail to the new node
                        return;
                    }
                } else {
                    tail.compareAndSet(currentTail, tailNext); // Tail has changed, try to move it forward
                }
            }
        }
    }

    public T dequeue() {
        while (true) {
            Node<T> currentHead = head.get();
            Node<T> headNext = currentHead.next.get();
            Node<T> currentTail = tail.get();
            if (currentHead == head.get()) { // Check if head has not changed
                if (currentHead == currentTail) { // Queue is empty or tail is falling behind
                    if (headNext == null) {
                        return null; // Queue is empty
                    }
                    tail.compareAndSet(currentTail, headNext); // Tail is falling behind, try to move it forward
                } else {
                    T value = headNext.value; // Get the value of the next node
                    if (head.compareAndSet(currentHead, headNext)) { // Move the head to the next node
                        return value;
                    }
                }
            }
        }
    }
}
