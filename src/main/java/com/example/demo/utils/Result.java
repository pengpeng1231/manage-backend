package com.example.demo.utils;

import com.example.demo.enums.ResultMessage;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer code;
    private String message;
    private T data;
    private Boolean success;

    // 成功静态方法
    public static <T> Result<T> ok(T data, String message) {
        Result<T> result = new Result<>();
        result.setCode(0);
        result.setMessage(message);
        result.setData(data);
        result.setSuccess(true);
        return result;
    }

    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.setCode(0);
        result.setMessage(ResultMessage.DEFAULT_SUCCESS.getMessage());
        result.setData(data);
        result.setSuccess(true);
        return result;
    }

    public static <T> Result<T> ok(int code, T data, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        result.setSuccess(true);
        return result;
    }

    public static <T> Result<T> ok(int code, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(ResultMessage.DEFAULT_SUCCESS.getMessage());
        result.setData(data);
        result.setSuccess(true);
        return result;
    }

    // 错误静态方法
    public static <T> Result<T> error(int code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setSuccess(false);
        return result;
    }

    public static <T> Result<T> error(int code, String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        result.setSuccess(false);
        return result;
    }

    public static <T> Result<T> error(int code) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(ResultMessage.DEFAULT_ERROR.getMessage());
        result.setSuccess(false);
        return result;
    }
}