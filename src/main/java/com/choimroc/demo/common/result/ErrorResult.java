package com.choimroc.demo.common.result;

import lombok.Getter;
import lombok.Setter;

/**
 * 带异常信息的返回结果
 *
 * @author choimroc
 * @since 2019/9/11
 */
@Getter
@Setter
public class ErrorResult extends Result {
    private String error;

    public ErrorResult(int code, String msg, String error) {
        super(code, msg);
        this.error = error;
    }
}
