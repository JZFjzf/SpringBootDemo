package com.choimroc.demo.common.exception;


import com.choimroc.demo.common.result.Result;
import com.choimroc.demo.common.result.ResultHelper;

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

    @ExceptionHandler(BindException.class)
    public Result bindExceptionHandler(final BindException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.OK.value());
//        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        String message = "";
        if (!e.getBindingResult().getAllErrors().isEmpty()) {
            DefaultMessageSourceResolvable defaultMessageSourceResolvable = new DefaultMessageSourceResolvable(e.getBindingResult().getAllErrors().get(0));
            message = defaultMessageSourceResolvable.getDefaultMessage();
        }

        return ResultHelper.badRequest(message);
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
        return ResultHelper.badRequest(message);
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
        return ResultHelper.badRequest(message);
    }

    @ExceptionHandler(CustomException.class)
    public Result customExceptionHandler(final CustomException exception, HttpServletResponse response) {
        response.setStatus(HttpStatus.OK.value());
        return new Result(exception.getCode(), exception.getMessage(), exception.getError());
    }

    @ExceptionHandler(SQLException.class)
    public Result sqlException(final SQLException exception, HttpServletResponse response) {
        response.setStatus(HttpStatus.OK.value());
        return ResultHelper.badRequest(exception.getMessage(), "数据库操作失败");
    }

}
