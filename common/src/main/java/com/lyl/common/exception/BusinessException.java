package com.lyl.common.exception;

import java.text.MessageFormat;

/**
 * 业务类异常
 *
 * @author WRQ
 * @date 2019/6/25
 * @since 1.0.0
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String pattern, Object ... arguments) {
        this(MessageFormat.format(pattern, arguments));
    }

    public BusinessException(String message, Throwable e) {
        super(message, e);
    }
}
