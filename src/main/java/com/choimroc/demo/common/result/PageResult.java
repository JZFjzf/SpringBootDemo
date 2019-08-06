package com.choimroc.demo.common.result;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author choimroc
 * @since 2019/5/3
 */
@Setter
@Getter
public class PageResult<T> extends Result {
    //总记录数
    private long total;
    //页码
    private long pageNumber;
    //页数
    private long pages;
    //数据
    private List<T> data;
}
