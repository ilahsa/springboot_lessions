package com.alta.hello.tools.core;

import java.time.Instant;
import java.util.Random;

/**
 * Created by GD.
 */
public class Randoms {

    private static final Random random = new Random(Instant.now().toEpochMilli());

    public static int randomIdx(int size) {
        return random.nextInt(size);
    }
}
