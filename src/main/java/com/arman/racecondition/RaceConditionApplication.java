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
        AtomicInteger counter = new AtomicInteger(0);
        try (ExecutorService executorService = Executors.newFixedThreadPool(8)) {
            for (int i = 0; i < 3; i++) {
                executorService.submit(new Producer(queue));
            }

            for (int i = 0; i < 5; i++) {
                executorService.submit(new Consumer(queue, counter));
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }
}
