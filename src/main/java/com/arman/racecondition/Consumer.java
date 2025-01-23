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
            //solution 1
            while (true) {
                synchronized (counter){
                    if (counter.get() >= 100) {
                        break;
                    }
                    Integer number = queue.take();
                    System.out.println("Items processed: " + counter + " Consumed: " + number);
                    counter.incrementAndGet();
                }
            }
            System.exit(0);

            //solution 2
//            while (true) {
//                int currentCount = counter.get();
//                if (currentCount >= 100) {
//                    break;
//                }
//                Integer number = queue.take();
//                if (counter.compareAndSet(currentCount, currentCount + 1)) {
//                    System.out.println("Items processed: " + counter + " Consumed: " + number);
//                }
//            }
//            System.exit(0);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}