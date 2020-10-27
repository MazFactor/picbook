package com.jinghuan.common.exception;

import java.text.MessageFormat;

/**
 * 应用类异常
 *
 * @author WRQ
 * @date 2019/6/25
 * @since 1.0.0
 */
public class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String pattern, Object ... arguments) {
        this(MessageFormat.format(pattern, arguments));
    }

    public ApplicationException(String message, Throwable e) {
        super(message, e);
    }
}
