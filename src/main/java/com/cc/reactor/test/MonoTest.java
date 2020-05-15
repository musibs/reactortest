package com.cc.reactor.test;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoTest {

    @Test
    void monoTest() {
        Mono.just("Spring")
                .log()
                .subscribe();
    }

    @Test
    void monoWithConsumer() {
        Mono.just("Spring")
                .log()
                .subscribe(System.out::println);
    }

    @Test
    void monoWithDoOn() {
        Mono.just("Spring")
                .log()
                .doOnSubscribe(subs -> System.out.println("Subscribe: "+subs))
                .doOnRequest(req -> System.out.println("Request: "+req))
                .doOnSuccess(complete -> System.out.println("Complete: "+complete))
                .subscribe(System.out::println);
    }

    @Test
    void emptyMono() {
        Mono.empty()
                .log()
                .subscribe();
    }

    @Test
    void emptyCompleteConsumerMono() {
        Mono.empty()
                .log()
                .subscribe(System.out::println, null, () -> System.out.println("Completed"));
    }

    @Test
    void errorRuntimeExceptionMono(){
        Mono.error(new RuntimeException())
                .log()
                .subscribe();
    }

    @Test
    void errorRuntimeExceptionMonoOnError(){
        Mono.error(new RuntimeException("Oho! Error Occurred!"))
                .log()
                .subscribe(System.out::println, (e) -> System.out.println(e.getMessage()), () -> System.out.println("Completed"));
    }

    @Test
    void monoDoOnError(){
        Mono.error(new Exception("Checked Exception"))
                .doOnError((e) -> System.out.println(e.getMessage()))
                .log()
                .subscribe();
    }

    @Test
    void monoDoOnErrorResume(){
        Mono.error(new Exception("Checked Exception"))
                .onErrorResume( e -> {
                    System.out.println(e.getMessage());
                    return Mono.just("Spring Boot in Practice ");
                })
                .log()
                .subscribe(System.out::println);
    }

    @Test
    void monoOnErrorReturn() {
        Mono.error(new RuntimeException())
                .onErrorReturn("Spring Boot in Practice")
                .log()
                .subscribe(System.out::println);
    }
}
