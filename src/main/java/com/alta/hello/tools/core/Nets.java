package com.alta.hello.tools.core;

import com.alta.hello.tools.exception.SysErrorMsg;
import com.alta.hello.tools.exception.SysException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Nets {
    public static boolean exist(String url) {
        if (BaseUtil.isNullOrEmptyOrEmptyStr(url)) {
            return false;
        }
        try {
            URL serverUrl = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
            String message = urlConnection.getHeaderField(0);
            if (BaseUtil.isNotNullOrEmpty(message) && message.indexOf("200") > -1) {
                return true;
            }
        } catch (IOException e) {
        }
        return false;
    }

    public static boolean notExist(String url) {
        return !exist(url);
    }

    public static void ifExist(String url, SysErrorMsg errorMsg) throws SysException {
        if (notExist(url)) {
            throw new SysException(errorMsg);
        }
    }
}
