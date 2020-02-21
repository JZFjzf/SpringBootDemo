package com.choimroc.mybatisplusdemo.common.exception;


import com.choimroc.mybatisplusdemo.common.locale.LocaleMessage;
import com.choimroc.mybatisplusdemo.common.result.ErrorResult;
import com.choimroc.mybatisplusdemo.common.result.Result;

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

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理
 *
 * @author choimroc
 * @since 2019/5/4
 */
@Slf4j
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
        logStackMsg(e);
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return handleErrorResult(message);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ConstraintViolationException.class)
    public Result violationExceptionHandler(final ConstraintViolationException e) {
        logStackMsg(e);
        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        return handleErrorResult(message);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidException(final MethodArgumentNotValidException e) {
        logStackMsg(e);
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return handleErrorResult(message);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(CustomException.class)
    public Result customExceptionHandler(final CustomException e) {
        logStackMsg(e);
        return handleErrorResult(e.getCode(), e.getMessage(), e.getError());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(SQLException.class)
    public Result sqlException(final SQLException e) {
        logStackMsg(e);
        return handleErrorResult(e.getMessage(), "数据库操作失败");
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NullPointerException.class)
    public Result nullPointerException(final NullPointerException e) {
        logStackMsg(e);
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

    private void logStackMsg(Exception e) {
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] stackArray = e.getStackTrace();
        for (StackTraceElement element : stackArray) {
            sb.append(element.toString()).append("\n");
        }

        log.error(sb.toString());
    }

}
