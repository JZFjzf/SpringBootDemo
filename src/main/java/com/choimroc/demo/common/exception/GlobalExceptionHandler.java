package com.choimroc.demo.common.exception;


import com.choimroc.demo.common.result.Result;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 *
 * @author choimroc
 * @since 2019/5/4
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(RuntimeException.class)
//    public Result runtimeExceptionHandler( final Exception e, HttpServletResponse response) {
//        response.setStatus(HttpStatus.OK.value());
//        RuntimeException exception = (RuntimeException) e;
//        return ResultHelper.badRequest(exception.getMessage());
//    }


    @ExceptionHandler(CustomException.class)
    public Result customExceptionHandler(final Exception e, HttpServletResponse response) {
        response.setStatus(HttpStatus.OK.value());
        CustomException exception = (CustomException) e;
        return new Result(exception.getCode(), exception.getMessage());
    }

}
