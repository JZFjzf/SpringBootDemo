package com.choimroc.demo.common;

import lombok.Data;

/**
 * restful统一返回
 *
 * @author choimroc
 * @since 2019/3/8
 */
@Data
public class Result<T> {
    private int code;
    private String msg;
//    private int count;
    private T data;
}
