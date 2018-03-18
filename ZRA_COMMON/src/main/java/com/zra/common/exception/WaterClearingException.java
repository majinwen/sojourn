package com.zra.common.exception;
/**
 * 智能水表结算清算异常类
 * @author lusp
 * @created 2018年03月1日 11:51:17
 */
public class WaterClearingException extends Exception {


    public WaterClearingException(String messageTmp, Object...args) {
        super(String.format(messageTmp,args));
    }

    public WaterClearingException() {
        super();
    }

    public WaterClearingException(String message) {
        super(message);
    }

    public WaterClearingException(String message, Throwable cause) {
        super(message, cause);
    }

    public WaterClearingException(Throwable cause) {
        super(cause);
    }

    protected WaterClearingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}