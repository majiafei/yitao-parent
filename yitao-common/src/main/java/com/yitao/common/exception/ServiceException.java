package com.yitao.common.exception;

/**
 * @ProjectName: house
 * @Package: com.yitao.common.exception
 * @ClassName: ServiceException
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/5 10:56
 */
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
