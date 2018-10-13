package com.alta.hello.tools.exception;

/**
 * Created by GD on 2017/12/19.
 */
public class SysException extends AbstractSysException {
    public SysException(SysErrorMsg sysErrorMsg) {
        super(sysErrorMsg);
    }
    public SysException(Throwable throwable) {
        super(throwable);
    }
}
