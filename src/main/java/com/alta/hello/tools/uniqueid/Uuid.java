package com.alta.hello.tools.uniqueid;

import com.alta.hello.tools.core.Consts;

import java.util.UUID;

/**
 * Created by GD on 2018/4/18.
 */
public class Uuid {

    public static String rdmUuid() {
        return UUID.randomUUID().toString().replaceAll(Consts.HORIZONTAL, Consts.EMPTY_STR);
    }
}
