package com.parkey.modernjava.rxjava;

import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
@Slf4j
public class DistinctPractice2 {
    public static void main(String[] args) throws InterruptedException {
        FlowableProcessor<User> flowableProcessor = PublishProcessor.create();

        ExecutorService executorService1 = Executors.newFixedThreadPool(4);
//        ExecutorService executorService2 = Executors.newFixedThreadPool(4);
        flowableProcessor
                .delay(1, TimeUnit.SECONDS)
                .doOnNext(user -> log.info("발행 : "+ user))
                .groupBy(User::getId)
                .doOnNext(gf -> log.info("gf : {} key: {}", gf.toString(), gf.getKey()))
                .flatMap(gf -> gf.throttleLast(500, TimeUnit.MILLISECONDS))
                .doOnNext(s ->log.info("debounce : {}", s))
                .subscribe(user -> log.info("r2 : {}", user.getId()));

        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();

        for (int i = 0; i < 100; i++) {
            long leftLimit = 1l;
            long rightLimit = 3l;
            long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
            long generatedLong2 = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
            executorService1.submit(() -> {
                log.debug("call next : "+ generatedLong);
                flowableProcessor.onNext(new User(generatedLong));
                copyOnWriteArrayList.add(generatedLong);
                randomInterval();
            });
//            executorService2.submit(() -> {
//                flowableProcessor.onNext(new User(generatedLong));
//                copyOnWriteArrayList.add(generatedLong2);
//                randomInterval();
//            });
        }

        Thread.sleep(10000l);
        Object collect = copyOnWriteArrayList.stream()
                .map(o -> o.toString())
                .collect(Collectors.joining(","));

        log.info("collect {}", collect);
    }

    public static void randomInterval() {
        long leftLimit = 100l;
        long rightLimit = 300l;
        long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
        try {
            Thread.sleep(100l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}



@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
class User {
    Long id;
}
