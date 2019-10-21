package com.choimroc.demo.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.choimroc.demo.common.result.DataResult;
import com.choimroc.demo.common.result.ErrorResult;
import com.choimroc.demo.common.result.PageResult;
import com.choimroc.demo.common.result.Result;

import java.util.List;

/**
 * @author choimroc
 * @since 2019/3/8
 */
public class BaseController {
    public Result auto(boolean success) {
        return success ? ok() : badRequest();
    }

    public Result ok(String msg) {
        return new Result(200, msg);
    }

    public Result ok() {
        return ok("成功");
    }

    public <T> DataResult<T> data(String msg, T data) {
        DataResult<T> result = new DataResult<>();
        result.setCode(200);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public <T> DataResult<T> data(T data) {
        return data("成功", data);
    }

    public <T> PageResult<List<T>> page(IPage<T> page) {
        PageResult<List<T>> result = new PageResult<>();
        result.setCode(200);
        result.setMsg("成功");
        result.setPageNumber(page.getCurrent());
        result.setPages(page.getPages());
        result.setTotal(page.getTotal());
        result.setData(page.getRecords());
        return result;
    }

    public <T> PageResult<T> page(IPage page, T data) {
        PageResult<T> result = new PageResult<>();
        result.setCode(200);
        result.setMsg("成功");
        result.setPageNumber(page.getCurrent());
        result.setPages(page.getPages());
        result.setTotal(page.getTotal());
        result.setData(data);
        return result;
    }

    public Result badRequest(String msg, String error) {
        return new ErrorResult(400, msg, error);
    }

    public Result badRequest(String msg) {
        return badRequest(msg, null);
    }

    public Result badRequest() {
        return badRequest("失败");
    }
}
