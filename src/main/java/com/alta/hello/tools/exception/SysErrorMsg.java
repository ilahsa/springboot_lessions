package com.alta.hello.tools.exception;

import java.io.Serializable;

/**
 * 异常对象.
 *
 * Created by GD on 2017/12/19.
 */
public class SysErrorMsg implements Serializable {
    /**
     * 错误代码.
     */
    private int errorCode;
    /**
     * 错误信息.
     */
    private String message;
    /**
     * 附加信息.
     */
    private String info;

    /**
     * 全参构造方法.
     *
     * @param errorCode 错误代码.
     * @param message 错误信息.
     */
    public SysErrorMsg(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    /**
     * 全参构造方法.
     *
     * @param errorCode 错误代码.
     * @param message 错误信息.
     * @param info 附加信息.
     */
    public SysErrorMsg(int errorCode, String message, String info) {
        this.errorCode = errorCode;
        this.message = message;
        this.info = info;
    }

    /**
     * 获取错误代码.
     *
     * @return
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * 获取错误信息.
     *
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * 获取附加信息.
     *
     * @return
     */
    public String getInfo() {
        return info;
    }
}
