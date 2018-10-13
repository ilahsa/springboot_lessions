package com.alta.hello.tools.exception;


import com.alta.hello.tools.core.BaseUtil;
import com.alta.hello.tools.core.Consts;

/**
 * 自定义异常.
 *
 * Created by GD on 2018/4/9.
 */
public class AbstractSysException extends RuntimeException {

    private com.alta.hello.tools.exception.SysErrorMsg sysErrorMsg = Consts.UNDEFINED_ERROR;
    private String errorMsg = null;
    public AbstractSysException() {
        super();
    }

    public AbstractSysException(com.alta.hello.tools.exception.SysErrorMsg sysErrorMsg) {
        super();
        this.sysErrorMsg = sysErrorMsg;
    }

    public AbstractSysException(String message) {
        super(message);
        this.errorMsg = message;
    }

    public AbstractSysException(String message, com.alta.hello.tools.exception.SysErrorMsg sysErrorMsg) {
        super(message);
        this.errorMsg = message;
        this.sysErrorMsg = sysErrorMsg;
    }

    public AbstractSysException(String message, Throwable cause) {
        super(message, cause);
        this.errorMsg = message;
    }

    public AbstractSysException(String message, Throwable cause, com.alta.hello.tools.exception.SysErrorMsg sysErrorMsg) {
        super(message, cause);
        this.errorMsg = message;
        this.sysErrorMsg = sysErrorMsg;
    }

    public AbstractSysException(Throwable cause) {
        super(cause);
    }

    public AbstractSysException(Throwable cause, com.alta.hello.tools.exception.SysErrorMsg sysErrorMsg) {
        super(cause);
        this.sysErrorMsg = sysErrorMsg;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("errorCode: ").append(getErrorCode()).append(", ");
        sb.append("errorMsg: ").append(getOriginMessage()).append(", ");
        sb.append("errorInfo: ").append(getErrorInfo());
        return sb.toString();
    }

    public String getOriginMessage(){
        if (sysErrorMsg == null) {
            return super.getMessage();
        }
        return BaseUtil.isNotNullOrEmpty(errorMsg) ? errorMsg
                : sysErrorMsg.getMessage();
    }

    public int getErrorCode() {
        return sysErrorMsg != null ? sysErrorMsg.getErrorCode() : Consts.UNDEFINED_ERROR_CODE;
    }

    public String getErrorInfo() {
        return sysErrorMsg != null ? sysErrorMsg.getInfo() : sysErrorMsg.getMessage();
    }

    public com.alta.hello.tools.exception.SysErrorMsg getSysErrorMsg() {
        return sysErrorMsg;
    }
}
