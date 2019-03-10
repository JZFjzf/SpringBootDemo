package com.choimroc.demo.base;

import javax.servlet.http.HttpServletRequest;

/**
 * @author choimroc
 * @since 2019/3/8
 */
public class BaseController {

    /**
     * 获取当前请求的token
     */
    public String getToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (token == null) {
            token = request.getParameter("token");
        }
        return token;
    }
}
