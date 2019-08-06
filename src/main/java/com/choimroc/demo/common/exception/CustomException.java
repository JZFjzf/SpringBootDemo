package com.choimroc.demo.common.exception;

/**
 * @author choimroc
 * @since 2019/5/4
 */
public class CustomException extends RuntimeException {

    private int code = 500;

    public CustomException() {
        super();
    }

    public CustomException(int code, String message) {
        super(message);
        this.setCode(code);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
