package com.example.demo.enums;

import lombok.Getter;

@Getter
public enum ResultMessage {
    DEFAULT_SUCCESS("操作成功"),
    DEFAULT_ERROR("操作失败");

    private final String message;

    ResultMessage(String message) {
        this.message = message;
    }
}
