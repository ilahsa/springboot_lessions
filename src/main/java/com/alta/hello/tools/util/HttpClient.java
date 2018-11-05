package com.alta.hello.tools.util;

import com.alta.hello.tools.core.BaseUtil;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by baiba on 2018-10-25.
 */
public class HttpClient {

    public static boolean exist(String url) {
        if (BaseUtil.isNullOrEmptyOrEmptyStr(url)) {
            return false;
        }
        try {
            URL serverUrl = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
            String message = urlConnection.getHeaderField(0);
            if (org.springframework.util.StringUtils.hasText(message) && message.indexOf("200") > 0) {
                return true;
            }
        } catch (IOException e) {
        }
        return false;
    }

    public static boolean notExist(String url) {
        return !exist(url);
    }

    public static boolean checkUrl(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }
        try {
            URL serverUrl = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
            String message = urlConnection.getHeaderField(0);
            if (StringUtils.hasText(message) && message.indexOf("200") > 0) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
        }
        return false;
    }

}
