package com.parkey.modernjava.ch05;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TestC {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> welcomeText = CompletableFuture.supplyAsync(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                throw new IllegalStateException(e);
//            }
            log.info("1");
            return "Rajeev";
        }).thenApply(name -> {
            log.info("2");
            return "Hello " + name;
        }).thenApply(greeting -> {
            log.info("3");
            return greeting + ", Welcome to the CalliCoder Blog";
        });

        log.info(welcomeText.get());

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                throw new IllegalStateException(e);
//            }
            log.info("future supply");
            return "Some Result";
        }).thenApply(result -> {
    /*
      Executed in the same thread where the supplyAsync() task is executed
      or in the main thread If the supplyAsync() task completes immediately (Remove sleep() call to verify)
    */      log.info("future apply");
            return "Processed Result";
        });
        log.info("future {}", future.get());

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                throw new IllegalStateException(e);
//            }
            log.info("future1 supply");
            return "Some Result";
        }).thenApplyAsync(result -> {
            // Executed in a different thread from ForkJoinPool.commonPool()
//            try {
//                TimeUnit.SECONDS.sleep(3);
//            } catch (InterruptedException e) {
//                throw new IllegalStateException(e);
//            }
            log.info("future1 apply async");
            return result + " " + "Processed Result";
        });
        log.info("{}", future1.get());
    }
}
