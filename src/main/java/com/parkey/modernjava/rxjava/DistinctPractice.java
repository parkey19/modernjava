package com.parkey.modernjava.rxjava;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.internal.operators.flowable.FlowablePublish;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DistinctPractice {
    public static void main(String[] args) throws InterruptedException {
        FlowableProcessor<Long> flowableProcessor = PublishProcessor.create();
        ExecutorService executorService1 = Executors.newFixedThreadPool(4);
        ExecutorService executorService2 = Executors.newFixedThreadPool(4);
        ExecutorService executorService3 = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 5000000; i++) {
            long leftLimit = 1L;
            long rightLimit = 3L;
            long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
            long generatedLong2 = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
            executorService1.submit(() -> {
                log.info("ex1 :{}", generatedLong);
                flowableProcessor.onNext(generatedLong);
            });
            executorService2.submit(() -> {
                log.info("ex2 :{}", generatedLong2);
                flowableProcessor.onNext(generatedLong2);
            });
        }

        flowableProcessor
                .onBackpressureBuffer()
                .window(1, TimeUnit.SECONDS)

                .doOnNext(longFlowable -> {
                    log.info("window: {}", longFlowable.toString());
                })
                .switchMap(longFlowable -> longFlowable.distinct())
                .doOnNext(aLong -> {
                    log.debug("doOn {}", aLong);
                })
                .flatMap(aLong -> createLong(aLong))
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .subscribe(aLong -> {
                    log.info("result :{}", aLong);
                });





//        for (int i = 0; i < 5000; i++) {
//            executorService2.submit(() -> {
//                long leftLimit = 1L;
//                long rightLimit = 3L;
//                long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
//                flowableProcessor.onNext(generatedLong);
//            });
//        }
//
//        for (int i = 0; i < 5000; i++) {
//            executorService3.submit(() -> {
//                long leftLimit = 1L;
//                long rightLimit = 4L;
//                long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
//                flowableProcessor.onNext(generatedLong);
//            });
//        }

//        CountDownLatch countDownLatch = new CountDownLatch(1);
//        countDownLatch.await();
    }

    private static Flowable createLong(Long a) {
        List<Long> longs = Arrays.asList(a);
        return Flowable.fromArray(longs)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                ;

    }
}
