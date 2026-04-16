package com.whxy.campusbooktrade2.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(Exception.class)
    public R<String> handle(Exception e) {
        e.printStackTrace();
        return R.fail("系统异常：" + e.getMessage());
    }
}