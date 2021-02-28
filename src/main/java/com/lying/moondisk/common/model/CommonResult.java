package com.lying.moondisk.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonResult<T> {
    private boolean result;
    private String  message;
    private T       data;

    public CommonResult(boolean result, String message){
        this.result  = result;
        this.message = message;
    }
}
