package com.bruce.lambda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 最佳实践：
 * 以后所有 for 循环处理数据，全部统一用 StreamAPI 进行替换
 *
 * Stream 所有数据和操作被组合成流管道：
 * 一个数据源（可以是数组、集合、生成器函数、IO 管道）
 * 零或多个中间操作（把一个流变成另一个流）
 * 一个终止操作（产生最终结果）
 */
public class StreamDemo {
    private static String[] buffer = new String[1];

    public void a() {
        String a = "aaaa";
        System.out.println("a done...");
        buffer[0] = a; // 消息队列

        // 引入一个缓冲区，引入消息队列，就能实现全系统、全异步、不阻塞、不等待、实时响应
    }

    public void b(String arg) {
        arg = buffer[0];
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("metod b.....: " + arg) ;
    }


    public static void main(String[] args) {
        StreamDemo demo = new StreamDemo();
        System.out.println("1111");
        demo.a();

        new Thread(() -> {
            demo.b("aaa");
        }).start();

        System.out.println("22222");
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    static class Person {
        private String name;
        private String gender;
        private Integer age;
    }

    @Test
    public void mainaaa() {
        List<Person> list = Arrays.asList(
                new Person("雷 丰阳", "男", 16),
                new Person("王 五", "女", 20),
                new Person("赵 六", "男", 22),
                new Person("王 七", "女", 33),
                new Person("雷 二", "女", 18)
        );

        //迭代器 模式
        for (Person person : list) {
            // 1. 迭代的速度取决于数据量
            // 2. 数据还得有容器缓存
        }

        // 背压
        /**
         * 正压：
         * 数据的生产者给消费者的压力
         *
         *
         */
//        list.stream()
//                .filter(a -> {
//                    System.out.println("aaa");
//                });

        // data 是流动的，而不是靠迭代被动流动
        // 推拉模型：
        // 推 ：流模式，上游有数据，自动推给下游
        // 拉 ：迭代器模式，自己遍历，自己拉取

        Map<String, List<Person>> collect =
                list.stream()
                        .parallel()
                        .filter(s -> s.age > 17)
                        .collect(Collectors.groupingBy(t -> t.gender));
        // flow
        System.out.println(collect);

    }


    @Test
    public void mainbbb() {
        List<Person> list = Arrays.asList(
                new Person("雷 丰阳", "男", 16),
                new Person("王 五", "女", 20),
                new Person("赵 六", "男", 22),
                new Person("王 七", "女", 33),
                new Person("雷 二", "女", 18)
        );

        /**
         * 1. 挑出 age > 18 岁的人 拿到集合流等于拿到集合的深拷贝的值，流的所有操作都是流的元素引用
         * filter 、 map 、 flatMap 流里面的每一个元素都完整走一个流水线，才能轮到下一个元素
         * 第一个元素流经所有管道处理后，下一个元素才能继续执行完整管道流程
         *
         * 声明式：基于时间机制的回调
         *
         */
        Stream<String> sorted = list.stream()
                .limit(3)
                .filter(person -> { // 程序员不自己调用，发生这个事情的时候系统调用
                    System.out.println("filter: " + person.hashCode());
                    return person.age > 18;
                })
                .peek(person -> System.out.println("filter peek: " + person))
                .map(person -> {
                    System.out.println("Person: " + person.hashCode());
                    return person.name;
                })
                .peek(s -> System.out.println("map peek: " + s))
                .flatMap(ele -> {
                    String[] s = ele.split(" ");
                    return Arrays.stream(s);
                })
                .distinct()
                .sorted(String::compareTo);

    }

    @Test
    public void aaaaa() {
        // 1. 找出最大偶数
        List<Integer> list = Arrays.asList(1, 2, 3, 4, -2, 6, 7, 8, 9);

        // for 循环，挨个遍历找到偶数， temp = i, 下次找到的偶数和临时遍历比较
        int max = 0;
        for (Integer num : list) {
            if (num % 2 == 0) {
                max = num >= max ? num : max;
            }
        }
        System.out.println("最大偶数是： " + max);


        /**
         * 流的特性：
         *
         * 1. 流是 lazy 的，不用方法就不会被调用
         *
         * StreamAPI：
         * 1. 数据封装成流，集合类.stream
         * 2. 定义流式操作
         * 3. 获取最终结果
         */
        list.stream()
                .filter(ele -> {
                    System.out.println("正在进行 filter");
                    return ele % 2 == 0;
                })
                .max(Integer::compareTo)
                .ifPresent(System.out::println);

    }
}

















