package com.niugiaogiao.utils;

import lombok.Data;

@Data
public class IgnoreResponseResult<T> {
    private final T result;
    public static <T> IgnoreResponseResult<?> toResult(T result) {
        return new IgnoreResponseResult<>(result);
    }
}
