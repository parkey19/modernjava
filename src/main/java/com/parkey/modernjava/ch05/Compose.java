package com.parkey.modernjava.ch05;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class Compose {

    List<User> USERS = new ArrayList<>();
    private UserService userService = new UserService();

    public Compose() {
        for (int i = 0; i < 1000000; i++) {
            this.USERS.add(new User(i, String.valueOf(i)));
        }
    }



    public static void main(String[] args) throws ExecutionException, InterruptedException {
        log.info("main ");
        Compose compose = new Compose();
        List<Integer> findIds = Arrays.asList(1, 200, 300, 401, 504, 603, 704, 805, 909, 10000);
        List<CompletableFuture<User>> futureList = findIds.stream()
                .map(id -> compose.userService.getUsersDetail(id))
                .collect(Collectors.toList());


        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()]));

        CompletableFuture<UserGroup> userGroupCompletableFuture = allFutures.thenApply(v -> {
                    return futureList.parallelStream()
                            .map(userFuture -> userFuture.join())
                            .collect(Collectors.toList());
        }).thenApply(users -> {
            UserGroup userGroup1 = getUserGroup().apply(getJoinNames().apply(users), getIds().apply(users));
            return userGroup1;
        });

        log.info("CompletableFuture - " + userGroupCompletableFuture.get());


    }


    private static Function<List<User>, String> getJoinNames() {
        return users -> users.stream()
                .map(User::getName)
                .collect(Collectors.joining(","));
    }

    private static Function<List<User>, List<Integer>> getIds() {
        return users -> users.stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }

    private static BiFunction<String, List<Integer>, UserGroup> getUserGroup() {
        return (s, ids)-> new UserGroup(ids, s);
    }

    @Data
    @AllArgsConstructor
    @ToString
    private class User {
        private int id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    @ToString
    private static class UserGroup {
        private List<Integer> ids;
        private String names;
    }

    public class UserService {
        private User getUserDetails(int id) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("get user");
            return USERS.stream()
                    .filter(user -> user.getId() == id)
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
        }

        CompletableFuture<User> getUsersDetail(int userId) {
            return CompletableFuture.supplyAsync(() -> {
                return userService.getUserDetails(userId);
            });
        }
    }
}
