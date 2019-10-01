package com.choimroc.demo.common.exception;


import com.choimroc.demo.common.locale.LocaleMessage;
import com.choimroc.demo.common.result.ErrorResult;
import com.choimroc.demo.common.result.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理
 *
 * @author choimroc
 * @since 2019/5/4
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final LocaleMessage localeMessage;

    @Autowired
    public GlobalExceptionHandler(LocaleMessage localeMessage) {
        this.localeMessage = localeMessage;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BindException.class)
    public Result bindExceptionHandler(final BindException e) {
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return handleErrorResult(message);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ConstraintViolationException.class)
    public Result violationExceptionHandler(final ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        return handleErrorResult(message);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidException(final MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return handleErrorResult(message);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(CustomException.class)
    public Result customExceptionHandler(final CustomException e) {
        return handleErrorResult(e.getCode(), e.getMessage(), e.getError());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(SQLException.class)
    public Result sqlException(final SQLException e) {
        e.printStackTrace();
        return handleErrorResult(e.getMessage(), "数据库操作失败");
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NullPointerException.class)
    public Result nullPointerException(final NullPointerException e) {
        e.printStackTrace();
        return handleErrorResult(localeMessage.getMessage("common.error.server"), "NullPointerException");
    }

    private Result handleErrorResult(int code, String msg, String error) {
        return new ErrorResult(code, msg, error);
    }

    private Result handleErrorResult(String msg, String error) {
        return handleErrorResult(400, msg, error);
    }

    private Result handleErrorResult(String msg) {
        return handleErrorResult(msg, "");
    }

}
