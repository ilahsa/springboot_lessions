package com.alta.hello.service;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by baiba on 2019-01-15.
 */
public class LessMain {
    public static void main(String[] args) {
        List<Integer> ls = Lists.newArrayList();
        ls.add(1);
        ls.add(3);
        System.out.println(addUp(ls.stream()));
    }

    private static Integer addUp(Stream<Integer> numbers){
        return numbers.reduce(0,(acc,element)->acc+element);
    }

    private static void debug(Supplier<String> message){
        if(1==1){
            System.out.println(message.get());
        }
    }
}

