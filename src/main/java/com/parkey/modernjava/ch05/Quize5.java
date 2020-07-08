package com.parkey.modernjava.ch05;

import com.parkey.modernjava.ch04.Dish;
import com.parkey.modernjava.ch04.Type;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Quize5 {
    public static void main(String[] args) {
//        List<Dish> collect = Dish.menu.stream()
//                .takeWhile(dish -> dish.getType().equals(Type.MEAT))
//                .limit(2)
//                .collect(Collectors.toList());
//        collect.forEach(System.out::println);

        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader m = new Trader("mario", "Milan");
        Trader al = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(m, 2011, 710),
                new Transaction(m, 2011, 700),
                new Transaction(al, 2011, 950)
        );

        //q1 - 2011년 트랜잭션만, 값을 오름차순 정렬
        List<Transaction> q1Result = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());

        q1Result.forEach(System.out::println);
        System.out.println("----");
        //q2 거래자가 근무하는 도시 이름을 중복 없이
        String collect = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(Collectors.joining(","));

        System.out.println(collect);
        System.out.println("----");
        //q3 케임브리지에서 근무하는 모든 거래자 찾아서 이름순 나열
        String names = transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.joining(","));

        System.out.println(names);
        System.out.println("----");
        //q4 이름 알파벳
        String orderNames = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .sorted()
                .collect(Collectors.joining(","));

        System.out.println(orderNames);
        System.out.println("----");
        //q5 밀란 근무자 있나?
        boolean isMilan = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
        System.out.println(isMilan);

        System.out.println("----");
        //q6 cambrige value print
        transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .forEach(System.out::println);

        System.out.println("----");

        //q7
        Optional<Integer> max = transactions.stream()
                .map(Transaction::getValue)
                .max(Integer::compareTo);
        System.out.println(max.orElse(-1));

        //q8
        Optional<Integer> min = transactions.stream()
                .map(Transaction::getValue)
                .min(Integer::compareTo);

        System.out.println(min.orElse(-1));

    }
}
