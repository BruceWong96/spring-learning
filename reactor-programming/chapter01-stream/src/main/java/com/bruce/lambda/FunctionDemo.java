package com.bruce.lambda;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionDemo {
    public static void main(String[] args) {
        // 1. 定义数据提供者函数
        Supplier<String> supplier = () -> "100a";
        // 2. 断言：验证是否是一个数字
        Predicate<String> isNumber = str -> str.matches("-?\\d+(\\.\\d+)?");
        // 3. 转换器：把字符编程数字
        Function<String, Integer> transfer = Integer::parseInt;

        // 4. 消费者：打印数字
        Consumer<Integer> consumer = integer -> {
            if (integer % 2 ==  0) {
                System.out.println(integer + " 是偶数。");
            } else {
                System.out.println(integer + " 是奇数。");
            }
        };
        method(supplier, isNumber, transfer, consumer);
    }

    private static void method(Supplier<String> supplier,
                               Predicate<String> isNumber,
                               Function<String, Integer> change,
                               Consumer<Integer> consumer) {
        if (isNumber.test(supplier.get())) {
            consumer.accept(change.apply(supplier.get()));
        } else {
            System.out.println("非法的数字！");
        }
    }
}
