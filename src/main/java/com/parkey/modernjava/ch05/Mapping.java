package com.parkey.modernjava.ch05;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.parkey.modernjava.ch04.Dish;

public class Mapping{

  public static void main(String... args) {
    // map
//    List<String> dishNames = Dish.menu.stream()
//        .map(Dish::getName)
//        .collect(toList());
//    System.out.println(dishNames);

    // map
    List<String> words = Arrays.asList("Hello", "World");
    List<Integer> wordLengths = words.stream()
        .map(String::length)
        .collect(toList());
    System.out.println(wordLengths);

    String[] strings = {"Hello", "World"};
    List<String> list = Stream.of(strings)
            .map(s -> s.split(""))
            .flatMap(Arrays::stream)
            .distinct()
            .collect(toList());
    list.forEach(System.out::println);
//    System.out.println(collect);

    // flatMap
    words.stream()
        .flatMap((String line) -> Arrays.stream(line.split("")))
        .distinct()
        .forEach(System.out::println);

    List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
    List<Integer> collect = integers.stream()
            .map(i -> i * i)
            .collect(toList());
    collect.forEach(System.out::println);

    List<Integer> integers1 = Arrays.asList(1, 2, 3);
    List<Integer> integers2 = Arrays.asList(3, 4);


    List<int[]> collect1 = integers1.stream()
            .flatMap(i -> integers2.stream()
                    .filter(i1 -> (i1+i)%3 == 0) //3으로 나누어 떨어지는 경우에만
                    .map(i1 -> new int[]{i, i1}))
            .collect(toList());
    collect1.forEach(ints -> System.out.println(ints[0] + "," + ints[1]));
//            .forEach(System.out::println);

//
//    // flatMap
//    List<Integer> numbers1 = Arrays.asList(1,2,3,4,5);
//    List<Integer> numbers2 = Arrays.asList(6,7,8);
//    List<int[]> pairs = numbers1.stream()
//        .flatMap((Integer i) -> numbers2.stream()
//            .map((Integer j) -> new int[]{i, j})
//        )
//        .filter(pair -> (pair[0] + pair[1]) % 3 == 0)
//        .collect(toList());
//    pairs.forEach(pair -> System.out.printf("(%d, %d)", pair[0], pair[1]));
  }

}
