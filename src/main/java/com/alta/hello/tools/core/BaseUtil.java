package com.alta.hello.tools.core;

import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalDouble;

/**
 * Created by GD on 2017/12/13.
 */
public class BaseUtil {
    public static boolean isNullOrEmpty(String str) {
        if (null == str || 0 == str.length()) {
            return true;
        }
        return false;
    }

    public static boolean isNullOrEmptyOrEmptyStr(String str) {
        if (isNullOrEmpty(str) || com.alta.hello.tools.core.Consts.NULL_STR.equals(str.trim().toLowerCase())) {
            return true;
        }
        return false;
    }

    public static boolean isNotNullOrEmpty(String str) {
        return !isNullOrEmpty(str);
    }

    public static boolean isNotNullOrEmpty(String... strs) {
        if (null != strs && strs.length > 0) {
            Optional optional = Arrays.stream(strs).filter(one -> isNullOrEmpty(one)).findAny();
            return optional.isPresent() ? false : true;
        }
        return false;
    }

    public static boolean equalZero(float num) {
        if (0 == num) {
            return true;
        }
        return false;
    }

    public static boolean notEqualZero(float num) {
        return !equalZero(num);
    }

    public static boolean notAllZero(double... nums) {
        OptionalDouble optional = Arrays.stream(nums).filter(num -> 0 != num).findAny();
        if (optional.isPresent()) {
            return true;
        }
        return false;
    }

    public static boolean isBetween(int num, int minNum, int maxNum) {
        if (num < minNum && num > maxNum) {
            return false;
        }
        return true;
    }

    public static boolean isNotBetween(int num, int minNum, int maxNum) {
        return !isBetween(num, minNum, maxNum);
    }

    public static int min(int one, int two) {
        return one > two ? two : one;
    }
}
