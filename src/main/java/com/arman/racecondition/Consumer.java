package com.arman.racecondition;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements Runnable {
    private final BlockingQueue<Integer> queue;
    private final AtomicInteger counter;

    public Consumer(BlockingQueue<Integer> queue, AtomicInteger counter) {
        this.queue = queue;
        this.counter = counter;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Integer number = queue.take();
                System.out.println("Items processed: " + counter + " Consumed: " + number);
                if (counter.incrementAndGet() >= 100) {
                    System.exit(0);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}