package com.alta.hello.tools.core;

import com.synative.cs.tools.exception.SysErrorMsg;
import com.synative.cs.tools.exception.SysException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * 转化工具.
 *
 * @author GD
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
public class Converts {

    /**
     * 字符串null转空.
     *
     * <p>
     *     如果字符串为null, 则返回空, 否则返回原字符串.
     * </p>
     *
     * @param str 待转化字符串
     * @return 转化后字符串
     */
    public static String null2Empty(String str) {
        return null == str ? Consts.EMPTY_STR : str;
    }

    /**
     * 字符串null转默认值.
     *
     * <p>
     *      如果字符串为null, 则返回默认值, 否则返回原字符串.
     * </p>
     *
     * @param str 待转化字符串
     * @param defaultValue 默认值
     * @return 转化后字符串
     */
    public static String null2Default(String str, String defaultValue) {
        return null == str ? defaultValue : str;
    }

    public static String nullOrNullStr2Empty(String str) {
        return nullOrNullStr2Default(str, Consts.EMPTY_STR);
    }

    public static String nullOrNullStr2Default(String str, String defaultStr) {
        return (null == str || Consts.NULL_STR.equals(str.toLowerCase())) ? defaultStr : str;
    }

    /**
     * Integer包装类对象null转0.
     *
     * <p>
     *     如果Integer包装类为null, 则转化为0, 否则保持原值.
     *     <br>
     *     转为基本数据类型.
     * </p>
     *
     * @param num 待转化Integer包装类对象
     * @return 基本数据类型int值
     */
    public static int null2Zero(Integer num) {
        return null == num ? Consts.ZERO_INT : num.intValue();
    }

    public static int null2Default(Integer num, int defaultValue) {
        return null == num ? defaultValue : num.intValue();
    }
    /**
     * int型数字0转默认值.
     *
     * <p>
     *     如果num为0, 则返回默认值, 否则返回原num.
     * </p>
     * @param num 待转化num
     * @param defaultValue 默认值
     * @return 转化后的值
     */
    public static int zero2Default(int num, int defaultValue) {
        return 0 == num ? defaultValue : num;
    }

    /**
     * Integer包装类对象null或0转默认值.
     *
     * @param num 待检测Integer对象
     * @param defaultValue 默认值
     * @return 转化结果
     */
    public static int nullOrZero2Default(Integer num, int defaultValue) {
        if (null == num || Consts.ZERO_INT == num.intValue()) {
            return defaultValue;
        }
        return num.intValue();
    }

    /**
     * Long包装类对象null转0.
     *
     * <p>
     *      如果Long包装类为null, 则转化为0, 否则保持原值.
     *      <br>
     *      转为基本数据类型.
     * </p>
     *
     * @param num 待转化Long包装类对象
     * @return 基本数据类型long值
     */
    public static long null2Zero(Long num) {
        return null == num ? Consts.ZERO_LONG : num.longValue();
    }

    public static long null2Zero(String num) {
        try {
            return null2Zero(Long.valueOf(num));
        } catch(Exception e) {
        }
        return Consts.ZERO_LONG;
    }

    public static long null2Default(Long num, long defaultValue) {
        return null == num ? defaultValue : num.longValue();
    }

    public static float null2Zero(Float num) {
        return null == num ? Consts.ZERO_FLOAT : num.floatValue();
    }

    public static float null2Default(Float num, float defaultValue) {
        return null == num ? defaultValue : num.floatValue();
    }

    /**
     * long型数字0转默认值.
     *
     * <p>
     *     如果num为0L, 则返回默认值, 否则返回原num.
     * </p>
     * @param num 待转化long类型num
     * @param defaultValue 默认值
     * @return 转化后的值
     */
    public static long zero2Default(long num, long defaultValue) {
        return 0L == num ? defaultValue : num;
    }

    /**
     * str字符串不足两位时,在字符串前补0.
     *
     * @param str 待检测字符串
     * @return 结果字符串
     */
    public static String addZeroBeLen2(String str) {
        return addCharBeLenNum(str, 2, '0');
    }

    /**
     * str字符串不足指定长度时,在字符串前补0.
     *
     * @param str 待检测字符串
     * @param minLength 最小长度
     * @param padChar 填充字符
     * @return 结果字符串
     */
    public static String addCharBeLenNum(String str, int minLength, char padChar) {
        Asserts.notNull(str);
        if (str.length() >= minLength) {
            return str;
        }
        StringBuilder sb = new StringBuilder(minLength);
        for (int i = str.length(); i < minLength; i++) {
            sb.append(padChar);
        }
        sb.append(str);
        return sb.toString();
    }

    public static String stackTraceToString(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw, true));
        return sw.getBuffer().toString();
    }

    public static String stackTraceToString(Throwable t, int depth) {
        StringBuilder sb = new StringBuilder(t.toString());
        StackTraceElement[] stes = t.getStackTrace();
        if (null != stes && CollectionUtil.isNotEmpty(stes)) {
            int size = BaseUtil.min(stes.length, depth);
            Arrays.stream(stes).limit(size).forEach(one -> sb.append(Consts.LINE_BREAK).append(one.toString()));
        }
        return sb.toString();
    }
}
