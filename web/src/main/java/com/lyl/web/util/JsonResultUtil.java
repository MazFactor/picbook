package com.lyl.web.util;

import com.lyl.web.base.Result.JsonResult;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;

import java.text.MessageFormat;

public class JsonResultUtil {
    private static final String MSG_ERROR_KEY_MYSQL_INTEGRITY_CONSTRAINT_VIOLATION = "Duplicate entry";

    public JsonResultUtil() {
    }

    public static <T> JsonResult<T> createJsonResult(boolean success, T data, String code, String message) {
        JsonResult<T> jsonResult = new JsonResult();
        jsonResult.setCode(0);
        jsonResult.setResult(data);
        jsonResult.setCode(Integer.valueOf(code));
        jsonResult.setMessage(message);
        return jsonResult;
    }

    public static <T> JsonResult<T> createSuccessJsonResult(T data, String statusCode, String message) {
        return createJsonResult(true, data, statusCode, message);
    }

    public static <T> JsonResult<T> createSuccessJsonResult(T data, String message) {
        return createSuccessJsonResult(data, String.valueOf(HttpStatus.OK.value()), message);
    }

    public static <T> JsonResult<T> createSuccessJsonResult(T data) {
        return createSuccessJsonResult(data, "操作成功！");
    }

    public static <T> JsonResult<T> createFailureJsonResult(T data, String statusCode, String message) {
        return createJsonResult(false, data, statusCode, message);
    }

    public static <T> JsonResult<T> createFailureJsonResult(T data, String message) {
        return createFailureJsonResult(data, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), message);
    }

    public static <T> JsonResult<T> createFailureJsonResult(String message) {
        return (JsonResult<T>) createFailureJsonResult((Object)null, (String)message);
    }

    public static <T> JsonResult<T> createFailureJsonResult(String format, Throwable e) {
        if (e == null || e.getMessage() == null || !DuplicateKeyException.class.equals(e.getClass()) && !e.getMessage().contains("Duplicate entry")) {
            return createFailureJsonResult(MessageFormat.format(format, e.getMessage()));
        } else {
            String param = e.getMessage().split("'")[1];
            String errMsg = MessageFormat.format("数据已存在:{0}！", param);
            return createFailureJsonResult(MessageFormat.format(format, errMsg));
        }
    }
}
