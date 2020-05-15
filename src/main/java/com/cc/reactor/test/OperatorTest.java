package com.cc.reactor.test;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.management.ThreadInfo;
import java.time.Duration;

public class OperatorTest {

    @Test
    void map() {
        Flux.range(1, 20)
                .log()
                .map(n -> n*10)
                .subscribe(System.out::println);
    }

    @Test
    void flatMap() {
        Flux.range(1, 5)
                .flatMap(n -> Flux.range(n*10, 2))
                .subscribe(System.out::println);
    }

    @Test
    void flatMapMany() {
        Mono.just(3)
                .flatMapMany(n -> Flux.range(1, n))
                .subscribe(System.out::println);
    }

    @Test
    void concat() throws Exception {
        Flux<Integer> oneToFive = Flux
                .range(1,5)
                .delayElements(Duration.ofMillis(200));

        Flux<Integer> sixToTen = Flux
                .range(6,5)
                .delayElements(Duration.ofMillis(400));

        Flux.concat(oneToFive, sixToTen)
                .subscribe(System.out::println);

        Thread.sleep(5000);
    }

    @Test
    void merge() throws Exception {
        Flux<Integer> oneToFive = Flux
                .range(1,5)
                .delayElements(Duration.ofMillis(200));

        Flux<Integer> sixToTen = Flux
                .range(6,5)
                .delayElements(Duration.ofMillis(400));

        Flux.merge(oneToFive, sixToTen)
                .subscribe(System.out::println);

        Thread.sleep(5000);
    }

    @Test
    void zip() throws Exception {
        Flux<Integer> oneToFive = Flux
                .range(1,5)
                .delayElements(Duration.ofMillis(200));

        Flux<Integer> sixToTen = Flux
                .range(6,5)
                .delayElements(Duration.ofMillis(400));

        Flux.zip(oneToFive, sixToTen, (item1, item2) -> item1+" <-> "+item2)
                .subscribe(System.out::println);

        Thread.sleep(5000);
    }
}
