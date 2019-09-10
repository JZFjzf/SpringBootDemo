package com.choimroc.demo.common.result;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author choimroc
 * @since 2019/4/24
 */
public class ResultHelper {

    public static Result auto(boolean success) {
        return success ? ok() : badRequest();
    }

    public static Result ok(String msg) {
        Result result = new Result();
        result.setCode(200);
        result.setMsg(msg);
        return result;
    }

    public static Result ok() {
        return ok("成功");
    }

    public static <T> DataResult<T> data(String msg, T data) {
        DataResult<T> result = new DataResult<>();
        result.setCode(200);
        result.setData(data);
        return result;
    }

    public static <T> DataResult<T> data(T data) {
        return data("", data);
    }

    public static <T> PageResult<T> page(IPage<T> page) {
        PageResult<T> result = new PageResult<>();
        result.setCode(200);
        result.setMsg("成功");
        result.setPageNumber(page.getCurrent());
        result.setPages(page.getPages());
        result.setTotal(page.getTotal());
        result.setData(page.getRecords());
        return result;
    }

    /**
     * 当前请求无法被服务器理解 或 请求参数有误
     */
    public static Result badRequest(String msg, String error) {
        Result result = new Result();
        result.setCode(400);
        result.setMsg(msg);
        result.setError(error);
        return result;
    }

    public static Result badRequest(String msg) {
        return badRequest(msg, null);
    }

    public static Result badRequest() {
        return badRequest("失败");
    }

    public static Result unauthorized() {
        Result result = new Result();
        result.setCode(401);
        result.setMsg("身份认证失败！");
        return result;
    }

    public static Result forbidden() {
        Result result = new Result();
        result.setCode(403);
        result.setMsg("您没有访问权限！");
        return result;
    }

    public static Result notFound(String msg) {
        Result result = new Result();
        result.setCode(404);
        result.setMsg(msg);
        return result;
    }

    public static Result internalServerError(String msg) {
        Result result = new Result();
        result.setCode(500);
        result.setMsg(msg);
        return result;
    }
}
