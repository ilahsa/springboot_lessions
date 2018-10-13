package com.alta.hello.tools.core;

import com.alta.hello.tools.exception.SysErrorMsg;

/**
 * Created by GD.
 */
public class Consts {

    /**
     * 空字符串.
     */
    public static final String EMPTY_STR = "";

    public static final String HORIZONTAL = "-";

    public static final String COMMA = ",";

    public static final String STAR = "*";

    public static final String LINE_BREAK = "\n";

    /**
     * float型0.
     */
    public static final float ZERO_FLOAT = 0f;

    public static final String ZERO_STR = "0";

    public static final int ZERO_INT = 0;

    /**
     * long型0.
     */
    public static final long ZERO_LONG = 0L;

    /**
     * null字符串.
     */
    public static final String NULL_STR = "null";

    /**
     * 冒号分隔符.
     */
    public static final String SPLIT_COLON = ":";

    /**
     * 年月.
     */
    public static final String YM = "yyyyMM";

    /**
     * 年月日.
     */
    public static final String YMD = "yyyyMMdd";

    /**
     * 年月日时分秒.
     */
    public static final String YMDHMS = "yyyyMMddHHmmss";

    /**
     * 年月日时分秒毫秒.
     */
    public static final String YMDHMSS = "yyyyMMddhhmmssSSS";

    /**
     * 格式化年月日时分秒毫秒.
     */
    public static final String YMDHMSS_COMMON_SYMBOL = "yyyy-MM-dd hh:mm:ss.SSS";

    /**
     * 系统调用成功.
     */
    public static final int SUCCESS_CODE = 0;

    public static final int UNDEFINED_ERROR_CODE = 99999;
    public static final String UNDEFINED_ERROR_MSG = "undefined error";
    public static final SysErrorMsg UNDEFINED_ERROR = new SysErrorMsg(UNDEFINED_ERROR_CODE, UNDEFINED_ERROR_MSG);

}
