package com.bruce.lambda;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.*;

/**
 * 只要是函数式接口都可以使用 Lambda 表达式简化
 * 函数式接口：接口中有且只有一个未实现的方法，这个接口就叫函数式接口
 */

interface MyInterface {
    int sum(int i, int j);
}


interface MyHaha {
    int haha();

    default int heihei() {
        return 2;
    }

    ; // 默认实现
}

interface My666 {
    void aaa(int i, int j, int k);
}

//检查注解，帮我们快速检查我们写的接口是否函数式接口
@FunctionalInterface
interface MyHehe {
    int hehe(int i);
}

// 实现类
class MyInterfaceImpl implements MyInterface {
    @Override
    public int sum(int i, int j) {
        return i + j;
    }
}

public class LambdaDemo {
    public static void main(String[] args) {

        // 声明一个函数
        BiConsumer<String, String> consumer = (a, b) -> {
            System.out.println("这是：" + a + "这是：" + b);
        };
        consumer.accept("1", "2");

        // 声明一个函数
        Function<String, Integer> function = (String x) -> Integer.parseInt(x);
        System.out.println("function test: " + function.apply("2"));

        /**
         * Supplier 是一个函数式接口，它不接受任何参数，但返回一个值。
         * 它定义了一个名为 get 的抽象方法，该方法不带参数，用于获取或生成一个值。
         * Supplier 主要用于延迟计算或提供一个生成值的机制。
         */
        Supplier<String> supplier = () -> UUID.randomUUID().toString();
        String s = supplier.get();
        System.out.println(s);

        /**
         * 接收两个参数，返回一个值的函数式接口
         */
        BiFunction<String, Integer, Long> biFunction = (a, b) -> 8788L;


        /**
         * Predicate 是一个函数式接口，它定义了一个用于测试输入值的抽象方法 test。
         * Predicate 主要用于表示一个断言（predicate），即一个可以对给定输入进行条件判断的函数。
         * 它返回一个布尔值，表示输入是否符合某个条件。
         */
        Predicate<Integer> even = (t) -> t % 2 == 0;
        //even.test(2);  正常判断
        //even.negate().test(2); 相反的判断
        System.out.println(even.negate().test(3));
    }

    @Test
    public void bbb() {
        ArrayList<String> names = new ArrayList<>();

        names.add("AAAA");
        names.add("BBBB");
        names.add("CCCC");
        names.add("DDDD");
        names.add("EEEE");

        // 比较器
        // 直接写函数接口
        Collections.sort(names, (o1, o2) -> o2.compareTo(o1));
        System.out.println(names);

        // class::function 引用类中的实例方法，忽略 lambda 的完整写法
        Collections.sort(names, String::compareTo);
        System.out.println(names);

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Hi");
                    }
                }
        ).start();

        Runnable runnable = () -> System.out.println("aaa");

        new Thread(runnable).start();

        /***
         * 最佳实践：
         * 1. 以后调用某个方法传入参数，这个参数实例是一个对象接口，且只定义了一个方法，就直接用 lambda 简化写法。
         *
         */
    }

    /**
     * lambda 简化函数式接口实例创建
     */
    @Test
    public void aaa() {

        // 1. 自己创建实现类对象
        MyInterface myInterface = new MyInterfaceImpl();
        System.out.println(myInterface.sum( 3, 4));

        // 2. 创建匿名实现类
        MyInterface myInterface1 = new MyInterface() {
            @Override
            public int sum(int i, int j) {
                return i * i + j * j;
            }
        };
        System.out.println(myInterface1.sum(1, 2));

        // 3. lambda 表达式 ：() -> {}
        MyInterface myInterface2 = (x, y) -> {
            return x * x + y * y;
        };
        System.out.println(myInterface2.sum(1, 2));

        // 参数位置最少的情况
        MyHaha myHaha = () -> {
            return 10;
        };

        MyHehe myHehe = y -> {
            return y * y;
        };

        MyHehe hehe2 = y -> y + 21;


        /**
         * 总结：
         * 1. Lambda 表达式：(参数列表) -> {方法体}
         * 2. 分辨出你的接口是否是函数式接口，函数式接口可以 lambda 简化
          */



    }
}
