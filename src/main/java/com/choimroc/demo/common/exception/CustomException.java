package com.choimroc.demo.common.exception;

/**
 * @author choimroc
 * @since 2019/5/4
 */
public class CustomException extends RuntimeException {

    private int code = 500;
    private String error = "自定义异常";

    public CustomException() {
        super();
    }

    public CustomException(int code, String message,String error) {
        super(message);
        this.setCode(code);
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
