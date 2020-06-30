package com.parkey.modernjava.ch05;

import com.parkey.modernjava.ch04.Dish;
import com.parkey.modernjava.ch04.Type;

import java.util.List;
import java.util.stream.Collectors;

public class Quize5_1 {
    public static void main(String[] args) {
        List<Dish> collect = Dish.menu.stream()
                .takeWhile(dish -> dish.getType().equals(Type.MEAT))
                .limit(2)
                .collect(Collectors.toList());
        collect.forEach(System.out::println);
    }
}
