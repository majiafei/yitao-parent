package com.yitao.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Package: com.yitao.common.exception
 * @ClassName: UploadException
 * @Author: majiafei
 * @Description:
 * @Date: 2019/6/1 10:09
 */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class UploadException extends RuntimeException{

    public UploadException(String message) {
        super(message);
    }

    public UploadException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
