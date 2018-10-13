package com.alta.hello.tools.core;

import com.alta.hello.tools.exception.SysErrorMsg;
import com.alta.hello.tools.exception.SysException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * 断言工具.
 *
 * @author GD
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
public class Asserts {
    /**
     * Object对象不为null.
     *
     * <p>
     *      校验对象obj是否为null.不为null时校验结束,为null时抛出异常.
     * </p>
     *
     * @param obj 待校验对象
     * @throws NullPointerException obj为null时抛出
     * @see #notNull(String, Object)
     */
    public static void notNull(Object obj) {
        notNull("param assert not null", obj);
    }

    public static void notNull(SysErrorMsg sysErrorMsg, Object object) {
        if (null == object) {
            throw new SysException(sysErrorMsg);
        }
    }

    /**
     * Object对象不为null.
     *
     * <p>
     *     校验对象obj是否为null.不为null时校验结束,为null时抛出异常.
     * </p>
     *
     * @param msg 异常信息
     * @param obj 待校验对象
     * @throws NullPointerException obj为null时抛出
     */
    public static void notNull(String msg, Object obj) {
        if (null == obj) {
            throw new NullPointerException(msg);
        }
    }

    /**
     * Object对象不为null，并且转化字符串后不为空，并且不为null(字符串).
     *
     * @param sysErrorMsg 异常对象
     * @param str 待检测Object对象
     */
    public static void notNullOrEmptyOrNullStr(SysErrorMsg sysErrorMsg, String str) {
        if (com.alta.hello.tools.core.BaseUtil.isNullOrEmptyOrEmptyStr(str)) {
            throw new SysException(sysErrorMsg);
        }
    }

    /**
     * 字符串不为null或空.
     *
     * <p>
     *      校验字符串str是否为null或空, 不为null或空时校验结束, 否则抛出异常.
     * </p>
     *
     * @param str 待校验字符串
     * @throws IllegalArgumentException str为null或空时抛出异常
     * @see #notNullOrEmpty(String, String)
     */
    public static void notNullOrEmpty(String str) {
        notNullOrEmpty("String assert not null or empty", str);
    }

    /**
     * 字符串不为null或空.
     *
     * <p>
     *     校验字符串str是否为null或空, 不为null或空时校验结束, 否则抛出异常.
     * </p>
     *
     * @param msg 异常信息
     * @param str 待校验字符串
     * @see BaseUtil#isNullOrEmpty(String)
     * @throws IllegalArgumentException str为null或空时抛出异常
     */
    public static void notNullOrEmpty(String msg, String str) {
        if (com.alta.hello.tools.core.BaseUtil.isNullOrEmpty(str)) {
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * 字符串不为null或空.
     *
     * <p>
     *     抛出自定义<code>SysErrorMsg</code>对象
     * </p>
     * @param sysErrorMsg
     * @param str
     */
    public static void notNullOrEmpty(SysErrorMsg sysErrorMsg, String str) {
        if (com.alta.hello.tools.core.BaseUtil.isNullOrEmpty(str)) {
            throw new SysException(sysErrorMsg);
        }
    }

    /**
     * 字符串数组不存在null或空.
     *
     * <p>
     *      校验字符串数组strs是否为null，或是否包含null和空, 不是null同时不包含时校验结束, 否则抛出异常.
     * </p>
     *
     * @param strs 待校验字符串数组
     * @throws IllegalArgumentException 为null或包含null和空时抛出异常
     * @see #notNullOrEmpty(String, String...)
     */
    public static void notNullOrEmpty(String... strs) {
        notNullOrEmpty("strs assert not null or any empty", strs);
    }

    /**
     * 字符串数组不存在null或空.
     *
     * <p>
     *     校验字符串数组strs是否为null，或是否包含null和空, 不是null同时不包含时校验结束, 否则抛出异常.
     * </p>
     *
     * @param msg 异常信息
     * @param strs 待校验字符串数组
     * @throws IllegalArgumentException 为null或包含null和空时抛出异常
     */
    public static void notNullOrEmpty(String msg, String... strs) {
        if (null == strs || 0 == strs.length) {
            throw new IllegalArgumentException(msg);
        }
        Optional<String> optional = Arrays.stream(strs)
                .filter(str -> (null == str || com.alta.hello.tools.core.Consts.EMPTY_STR.equals(str)))
                .findAny();
        if (optional.isPresent()) {
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * 集合不为null或空.
     *
     * <p>
     *     校验集合是否为null, 或者是否为空. 不是null或空时校验结束, 否则抛出异常.
     * </p>
     *
     * @param collection 待检测集合
     * @throws IllegalArgumentException 当集合为null或集合为空时抛出异常
     * @see #notNullOrEmpty(String, Collection)
     */
    public static void notNullOrEmpty(Collection collection) {
        notNullOrEmpty("collection assert not null or empty", collection);
    }

    /**
     * 集合不为null或空.
     *
     * <p>
     *     校验集合是否为null, 或者是否为空. 不是null或空时校验结束, 否则抛出异常.
     * </p>
     *
     * @param msg 异常信息
     * @param collection 待检测集合
     * @see CollectionUtil#isEmpty(Collection)
     * @throws IllegalArgumentException 当集合为null或集合为空时抛出异常
     */
    public static void notNullOrEmpty(String msg, Collection collection) {
        if (com.alta.hello.tools.core.CollectionUtil.isEmpty(collection)) {
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * 集合不为null或空.
     *
     * <p>
     *     抛出<code>SysErrorMsg</code>对象.
     * </p>
     * @param sysErrorMsg
     * @param collection
     * @throws SysException
     * @see CollectionUtil#isEmpty(Collection)
     */
    public static void notNullOrEmpty(SysErrorMsg sysErrorMsg, Collection collection) {
        if (com.alta.hello.tools.core.CollectionUtil.isEmpty(collection)) {
            throw new SysException(sysErrorMsg);
        }
    }

    /**
     * map不为null或空.
     *
     * <p>
     *      校验map是否为null, 或者是否为空. 不是null或空时校验结束, 否则抛出异常.
     * </p>
     *
     * @param map 待检测map
     * @throws IllegalArgumentException 当map为null或集合为空时抛出异常
     */
    public static void notNullOrEmpty(Map map) {
        notNullOrEmpty("map assert not null or empty", map);
    }

    /**
     * map不为null或空.
     *
     * <p>
     *      校验map是否为null, 或者是否为空. 不是null或空时校验结束, 否则抛出异常.
     * </p>
     *
     * @param msg 异常信息
     * @param map 待检测map
     * @throws IllegalArgumentException 当map为null或集合为空时抛出异常
     * @see CollectionUtil#isEmpty(Map)
     */
    public static void notNullOrEmpty(String msg, Map map) {
        if (com.alta.hello.tools.core.CollectionUtil.isEmpty(map)) {
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * 待检测值比基准值小.
     *
     * @param value 待检测值
     * @param basicValue 基准值
     * @throws IllegalArgumentException 当待检测值大于或等于基准值时抛出异常
     */
    public static void smallerThan(long value, long basicValue) {
        if (value >= basicValue ) {
            throw new IllegalArgumentException("value assert smaller than basicValue");
        }
    }

    /**
     * 待检测值不比基准值大.
     *
     * @param value 待检测值
     * @param basicValue 基准值
     * @throws IllegalArgumentException 当待检测值大于基准值时抛出异常
     */
    public static void notGreaterThan(long value, long basicValue) {
        if (value > basicValue) {
            throw new IllegalArgumentException("value assert not greater than basicValue");
        }
    }

    /**
     * 待检测值比基准值大.
     *
     * @param value 待检测值
     * @param basicValue 基准值
     * @throws IllegalArgumentException 当待检测值小于或等于基准值时抛出异常
     */
    public static void greaterThan(long value, long basicValue) {
        if (value <= basicValue) {
            throw new IllegalArgumentException("value assert greater than basicValue");
        }
    }

    /**
     * 待检测值不比基准值小.
     *
     * @param value 待检测值
     * @param basicValue 基准值
     * @throws IllegalArgumentException 当待检测值小于基准值时抛出异常
     */
    public static void notSmallerThan(long value, long basicValue) {
        if (value < basicValue) {
            throw new IllegalArgumentException("value assert not smaller than basicValue");
        }
    }
}
