package com.choimroc.demo.common.result;

import lombok.Getter;
import lombok.Setter;

/**
 * 带普通数据的返回结果
 *
 * @author choimroc
 * @since 2019/5/3
 */
@Getter
@Setter
public class DataResult<T> extends Result {
    private T data;
}
