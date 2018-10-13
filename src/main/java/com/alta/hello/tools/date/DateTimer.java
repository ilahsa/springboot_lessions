package com.alta.hello.tools.date;

import com.alta.hello.tools.core.Consts;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static java.time.LocalDateTime.now;

/**
 * 日期工具.
 *
 * @author GD
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
public class DateTimer {
    /** 年月 */
    public static final DateTimeFormatter FORMATTER_YM = DateTimeFormatter.ofPattern(Consts.YM);
    /** 年月日 */
    public static final DateTimeFormatter FORMATTER_YMD = DateTimeFormatter.ofPattern(Consts.YMD);
    /** 年月日时分秒 */
    public static final DateTimeFormatter FORMATTER_YMDHMS = DateTimeFormatter.ofPattern(Consts.YMDHMS);
    /** 年月日时分秒毫秒 */
    public static final DateTimeFormatter FORMATTER_YMDHMSS = DateTimeFormatter.ofPattern(Consts.YMDHMSS);
    /** 年-月-日 时:分:秒.毫秒 */
    public static final DateTimeFormatter FORMATTER_YMDHMSS_SYMBOL = DateTimeFormatter.ofPattern(Consts.YMDHMSS_COMMON_SYMBOL);

    /**
     * UTC时间戳long型转可读时间字符串.
     *
     * @param ins UTC时间戳
     * @return 可读时间字符串
     */
    public static String utcLong2Str(long ins) {
        return long2LocalDateTime(ins).format(FORMATTER_YMDHMSS_SYMBOL);
    }

    private static LocalDateTime long2LocalDateTime(long ins) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(ins), ZoneId.of("Z"));
    }

    public static String instantsStr() {
        return String.valueOf(Instant.now().toEpochMilli());
    }

    public static long instantsLong() {
        return Instant.now().toEpochMilli();
    }

    /**
     * 提供分钟级超时检测.
     *
     * <p>
     *     当初始时间为0 或者 超时时间为0时，直接认定为超时.
     * </p>
     *
     * @param initiate
     * @param interval
     * @return
     */
    public static boolean isTimeOut(long initiate, long interval) {
        if (Consts.ZERO_LONG == initiate || Consts.ZERO_LONG == interval) {
            return true;
        }
        long duration = ChronoUnit.MINUTES.between(long2LocalDateTime(initiate), now());
        return duration > interval ? true : false;
    }

    public static boolean isNotTimeOut(long initiate, long interval) {
       return !isTimeOut(initiate, interval);
    }
}
