package com.alta.hello.tools.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanMap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * Created by swann on 2018/05/16
 **/
public class CommonUtils {
    public static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);


    public static int nullToZero(Object o) {
        if (o == null) {
            return 0;
        } else {
            return Integer.valueOf(o.toString());
        }
    }

    /**
     *
     * @Param:
     * @return:
     * @Description:  去除数组中为空的
     */
    public static String[] filterArray(String... arr) {
        List<String> list = new ArrayList<>();
        for (String a : arr) {
            if (!StringUtils.isEmpty(a)) {
                list.add(a);
            }
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     *
     * @Param:
     * @return:
     * @Description: 将map转为对象
     */
    public static <T> T mapToBean(Map<String, String> map, T bean) {
        if (!map.isEmpty()) {
            BeanMap beanMap = BeanMap.create(bean);
            beanMap.putAll(map);
        }
        return bean;
    }

    /**
     *
     * @Param:
     * @return:
     * @Description: 将对象转为map
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }
    /**
     *
     * @Param:
     * @return:
     * @Description: 将对象转为map,并清除为空的key
     */
    public static <T> Map<String, Object> beanToMapExcludeNull(T bean) {
        Map<String, Object> map = beanToMap(bean);
        return checkMapValue(map);
    }

    /**
    *
    * @Param:
    * @return:
    * @Description:  将map中value转为string && 空的删除
    */
    public static Map<String, Object> checkMapValue(Map<String, Object> map) {
        Iterator<Map.Entry<String, Object>> ite = map.entrySet().iterator();
        while (ite.hasNext()) {
            Map.Entry<String, Object> entry = ite.next();
            String sv = String.valueOf(entry.getValue());
            if( entry.getValue() != null && (sv.length() == 0 || "null".equals(sv.toLowerCase())) ) {
                ite.remove();
            } else if(entry.getValue() == null){
                ite.remove();
            } else {
                entry.setValue(sv);
            }
        }
        return map;
    }

    /**
    *
    * @Param:
    * @return:
    * @Description:  将map中value转为string && 空替换为空字符串
    */
    public static Map<String, Object> checkMapValueHasNull(Map<String, Object> map) {
        Iterator<Map.Entry<String, Object>> ite = map.entrySet().iterator();
        while (ite.hasNext()) {
            Map.Entry<String, Object> entry = ite.next();
            String sv = String.valueOf(entry.getValue());
            if( entry.getValue() != null && (sv.length() == 0 || "null".equals(sv.toLowerCase())) ) {
                entry.setValue("");
            } else if(entry.getValue() == null){
                entry.setValue("");
            } else {
                entry.setValue(sv);
            }
        }
        return map;
    }

    /**
     *
     * @Param:
     * @return:
     * @Description: 检查list中的值是否都不为空
     */
    public static boolean checkValueList(List<? extends Object> list) {
        long retCount = 0;
        if(list != null) {
            retCount = list.stream().filter(value -> {
                if(value != null && (value + "").length() > 0) {
                    return true;
                } else {
                    return false;
                }
            }).count();
            return  retCount == list.size() ? true : false;
        }
        return false;
    }

    /**
     *
     * @Param:
     * @return:
     * @Description: 检查list中的值至少有一个不为空
     */
    public static boolean checkValueListHasNull(List<? extends Object> list) {
        if(list != null) {
            Optional<?> optional = list.stream().filter(value -> {
                if(value != null && (value + "").length() > 0) {
                    return true;
                } else {
                    return false;
                }
            }).findFirst();
            if (optional.isPresent()) {
                return true;
            }
        }
        return false;
    }

    /**
    *
    * @Param:
    * @return:
    * @Description:  微信公众号发送告警
    */
    public static void sentAlarmURL(String url, String content) {
        String alarmURL = url + content;
        try {
            URL realUrl = new URL(alarmURL);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            // 建立连接
            connection.connect();
            try(BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
                logger.info("alarm sending result: {}", sb.toString());
            }
        } catch (Exception e) {
            logger.error("Exception occur when send http get request!", e);
        }
    }
}
