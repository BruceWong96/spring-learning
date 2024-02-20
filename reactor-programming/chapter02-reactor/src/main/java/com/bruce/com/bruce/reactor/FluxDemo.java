package com.bruce.com.bruce.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.io.IOException;
import java.time.Duration;

public class FluxDemo {

    public static void main(String[] args) {
//        Flux.concat(Flux.just(1, 2, 3), Flux.just(7, 8, 9))
//                .subscribe(System.out::println);
        Flux.range(1, 7)
                .filter(i -> i > 3)
                .map(i -> "haha-" + i)
                .log()
                .subscribe(System.out::println);
    }


    /**
     * 测试 API
     * @param args
     */
    public void doOnXXX (String[] args) throws InterruptedException {
        /**
         * 事件感知 API ：触发回调（Hook 函数），doOnXXX;
         *
         */
        Flux<Integer> fluxData = Flux.range(1, 7)
                .delayElements(Duration.ofSeconds(1))
                .doOnComplete(() -> {
                    System.out.println("流正常结束...");
                })
                .doOnCancel(() -> {
                    System.out.println("流被取消了...");
                })
                .doOnError(throwable -> {
                    System.out.println("流出错了。。。" + throwable);
                }).doOnNext(integer -> {
                    System.out.println("doOnNext..." + integer);
                });

        fluxData.subscribe(System.out::println);
        Thread.sleep(20000);

    }

    /**
     * Mono test
     * @throws IOException
     */
    @Test
    public void mono() throws IOException {
        Mono<Integer> just = Mono.just(100);

        just.subscribe(System.out::println);
    }

    /**
     * Flux test
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void flux() throws IOException, InterruptedException {
        /**
         * Mono: 0 / 1 个元素的流
         * Flux: N 个元素的流
         * publisher 发布数据
         */
        // 多元素的流
        Flux<Integer> just = Flux.just(1, 2, 3, 4, 5, 6);

        // 流不消费就不会被使用，消费：订阅
        just.subscribe(e -> System.out.println("e1 = " + e));
        // 一个数据流可以有多个消费者
        just.subscribe(e -> System.out.println("e2 = " + e));

        //广播模式：对于每个消费者，流都是一样的；
        System.out.println("--------------------------------------");

        Flux<Long> fluxInterval = Flux.interval(Duration.ofSeconds(1));
        fluxInterval.subscribe(System.out::println);
    }
}
