package com.cuiyq.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @version V1.0
 * @Title:
 * @Description: 通用返回类
 * @Copyright 2024 Cuiyq
 * @author: Cuiyq
 * @date: 2024/3/28 12:56
 */
@Data
public class BaseResponse<T> implements Serializable {

    private int code;
    private String message;
    private T data;
    private String description;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }
    public BaseResponse(int code, T data, String message) {
        this(code, data, message, "");
    }
    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }
}
