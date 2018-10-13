package com.alta.hello.tools.util;

import java.util.regex.Pattern;

/**
 * Created by swann on 2018/05/14
 **/
public class StringUtils extends org.springframework.util.StringUtils{
    //非数字类型
    private static Pattern notNumPattern = Pattern.compile("[^0-9]");

    public static String checkNull(Object str) {
        if (str == null) {
            return "";
        } else {
            return ("" + str).trim();
        }
    }

    /**
    *
    * @Param:
    * @return:
    * @Description:  判断是否相等,有一个参数为null即为不等
    */
    public static boolean equalsStr(Object sourStr, Object destStr) {
        if (isEmpty(sourStr) || isEmpty(destStr)) {
            return false;
        } else {
            if (sourStr.toString().toLowerCase().equals(destStr.toString().toLowerCase())) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
    *
    * @Param:
    * @return:
    * @Description:  从字符串中提取数字
    */
    public static String getAllNums(String str) {
        return notNumPattern.matcher(str).replaceAll("").trim();
    }

    /**
    *
    * @Param:
    * @return:
    * @Description:  是否为空
    */
    public static boolean isEmpty(Object str) {
        if (str == null || "".equals(str) || "null".equals(str)) {
            return true;
        }
        return false;
    }

    public static String defalutIfEmpty(String str) {
        if (isEmpty(str)) {
            return "";
        } else {
            return str;
        }
    }

}
