package com.alta.hello.tools.core;

import java.util.Collection;
import java.util.Map;

/**
 * Created by GD on 2017/12/12.
 */
public class CollectionUtil {

    public static boolean isEmpty(Collection collection) {
        if (null == collection || collection.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmpty(Map map) {
        if (null == map || map.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }

    public static boolean isEmpty(Object[] objects) {
        if (null == objects || 0 == objects.length) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Object[] objects) {
        return !isEmpty(objects);
    }
}
