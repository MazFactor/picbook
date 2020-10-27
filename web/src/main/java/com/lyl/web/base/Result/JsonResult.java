package com.lyl.web.base.Result;

import java.io.Serializable;

public class JsonResult<T> implements Serializable {
    private int code;
    private T result;
    private String message;

    public JsonResult() {
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getResult() {
        return this.result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String toString() {
        return "JsonResult{, result=" + this.result + ",code='" + this.code + '\'' + ", message='" + this.message + '\'' + '}';
    }
}
