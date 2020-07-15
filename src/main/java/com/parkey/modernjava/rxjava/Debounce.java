package com.parkey.modernjava.rxjava;

import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Debounce {
    public static void main(String[] args) throws InterruptedException {
        String[] data = {"1", "2", "3", "5"};

        Observable<String> source = Observable.concat(
                Observable.timer(100L, TimeUnit.MILLISECONDS).map(i -> data[0]),
                Observable.timer(300L, TimeUnit.MILLISECONDS).map(i -> data[1]),
                Observable.timer(100L, TimeUnit.MILLISECONDS).map(i -> data[2]),
                Observable.timer(100L, TimeUnit.MILLISECONDS).map(i -> data[3]))
                .doOnNext(s -> log.debug("on : "+ s))
                .debounce(200L, TimeUnit.MILLISECONDS)
                ;

        source.subscribe(s -> log.debug("sub: {} ", s));
        Thread.sleep(1000);



    }
}
