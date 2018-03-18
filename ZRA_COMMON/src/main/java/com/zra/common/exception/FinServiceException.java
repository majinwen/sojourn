package com.zra.common.exception;
/**
 * 财务异常类
 * @author jixd
 * @created 2018年01月10日 10:23:17
 */
public class FinServiceException extends Exception {


    public FinServiceException(String messageTmp, Object...args) {
        super(String.format(messageTmp,args));
    }

    public FinServiceException() {
        super();
    }

    public FinServiceException(String message) {
        super(message);
    }

    public FinServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public FinServiceException(Throwable cause) {
        super(cause);
    }

    protected FinServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}