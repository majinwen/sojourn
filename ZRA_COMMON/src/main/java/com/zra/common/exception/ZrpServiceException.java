package com.zra.common.exception;
/**
 * 业务异常类
 * @author jixd
 * @created 2018年01月10日 10:23:17
 */
public class ZrpServiceException extends RuntimeException {


    public ZrpServiceException(String messageTmp, Object...args) {
        super(String.format(messageTmp,args));
    }

    public ZrpServiceException() {
        super();
    }

    public ZrpServiceException(String message) {
        super(message);
    }

    public ZrpServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZrpServiceException(Throwable cause) {
        super(cause);
    }

    protected ZrpServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}