package com.arman.racecondition;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class RaceConditionApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RaceConditionApplication.class, args);
    }


    @Override
    public void run(String... args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        AtomicInteger counter = new AtomicInteger(0);

        try {
            for (int i = 0; i < 3; i++) {
                executorService.submit(new Producer(queue));
            }

            for (int i = 0; i < 5; i++) {
                executorService.submit(new Consumer(queue, counter));
            }
        } finally {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
