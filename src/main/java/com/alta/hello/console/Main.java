package com.alta.hello.console;

import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Created by baiba on 2018-09-13.
 */
public class Main {
    public static void main(String[] args){
        IntStream.range(1,10)
        .forEach(i-> System.out.println(i));
    }
}

class	Something	{
    String	startsWith(String	s)	{
        return	String.valueOf(s.charAt(0));
    }
}

