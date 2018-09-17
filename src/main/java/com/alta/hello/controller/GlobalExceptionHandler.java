package com.alta.hello.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by baiba on 2018-09-14.
 * 全局异常信息捕获
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public Map handleGlobalException(HttpServletRequest req, Exception e) {
        //打印异常信息：
        e.printStackTrace();
        System.out.println("GlobalExceptionHandler.handleGlobalException()");
        Map map = new HashMap();
        map.put("code", 100);
        map.put("msg", e.getMessage());
        return map;
    }
}
