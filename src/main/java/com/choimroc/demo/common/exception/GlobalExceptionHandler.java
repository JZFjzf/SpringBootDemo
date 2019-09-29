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
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理
 *
 * @author choimroc
 * @since 2019/5/4
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final LocaleMessage localeMessage;

    @Autowired
    public GlobalExceptionHandler(LocaleMessage localeMessage) {
        this.localeMessage = localeMessage;
    }

    @ExceptionHandler(BindException.class)
    public Result bindExceptionHandler(final BindException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.OK.value());
//        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        String message = "";
        if (!e.getBindingResult().getAllErrors().isEmpty()) {
            DefaultMessageSourceResolvable defaultMessageSourceResolvable = new DefaultMessageSourceResolvable(e.getBindingResult().getAllErrors().get(0));
            message = defaultMessageSourceResolvable.getDefaultMessage();
        }

        return handleErrorResult(message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Result violationExceptionHandler(final ConstraintViolationException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.OK.value());
//        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        String message = "";
        for (ConstraintViolation item : e.getConstraintViolations()) {
            message = item.getMessage();
            break;
        }
        return handleErrorResult(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidException(final MethodArgumentNotValidException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.OK.value());
//        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        String message = "";
        if (!e.getBindingResult().getAllErrors().isEmpty()) {
            DefaultMessageSourceResolvable defaultMessageSourceResolvable = new DefaultMessageSourceResolvable(e.getBindingResult().getAllErrors().get(0));
            message = defaultMessageSourceResolvable.getDefaultMessage();
        }
        return handleErrorResult(message);
    }

    @ExceptionHandler(CustomException.class)
    public Result customExceptionHandler(final CustomException exception, HttpServletResponse response) {
        response.setStatus(HttpStatus.OK.value());
        return handleErrorResult(exception.getCode(), exception.getMessage(), exception.getError());
    }

    @ExceptionHandler(SQLException.class)
    public Result sqlException(final SQLException exception, HttpServletResponse response) {
        response.setStatus(HttpStatus.OK.value());
        return handleErrorResult(exception.getMessage(), "数据库操作失败");
    }

    @ExceptionHandler(NullPointerException.class)
    public Result nullPointerException(final NullPointerException exception, HttpServletResponse response) {
        exception.printStackTrace();
        response.setStatus(HttpStatus.OK.value());
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
