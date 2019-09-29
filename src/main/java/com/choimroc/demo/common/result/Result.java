package com.choimroc.demo.common.result;

import lombok.Getter;
import lombok.Setter;

/**
 * 统一返回结果
 *
 * @author choimroc
 * @since 2019/3/8
 */
@Setter
@Getter
public class Result {
    private int code;
    private String msg;

    public Result() {
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

