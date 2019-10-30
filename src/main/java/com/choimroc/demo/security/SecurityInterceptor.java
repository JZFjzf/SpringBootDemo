package com.choimroc.demo.security;


import com.choimroc.demo.annotation.IgnoreSecurity;
import com.choimroc.demo.annotation.Permission;
import com.choimroc.demo.common.exception.CustomException;
import com.choimroc.demo.tool.CacheUtils;
import com.choimroc.demo.tool.EncodeUtils;
import com.choimroc.demo.tool.ValidatorUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 拦截器
 *
 * @author choimroc
 * @since 2019/3/10
 */
@Slf4j
@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {
    private final CacheUtils cacheUtils;

    @Autowired
    public SecurityInterceptor(CacheUtils cacheUtils) {
        this.cacheUtils = cacheUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String requestPath = request.getRequestURI();
        if (requestPath.contains("/error")) {
            return true;
        }

        //判断接口是否需要认证
        IgnoreSecurity ignoreSecurity = method.getAnnotation(IgnoreSecurity.class);

        //不要验证
        if (ignoreSecurity != null) {
            return true;
        }

        int userId = parseToken(request);
        //如果refresh false一般就是key已经失效
        if (userId == 0 || !cacheUtils.refreshToken(userId)) {
            throw new CustomException(401, "身份认证失败,请重新登录", "token已失效");
        }

        //判断接口是否需要验证权限
        Permission permission = method.getAnnotation(Permission.class);
        //需要验证权限
        if (permission != null) {
            //TODO 查找用户信息进行比较
        }

        //验证通过，返回用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);

        request.setAttribute("CurrentUser", userInfo);


        return true;
    }


    /**
     * 解析token，获取userId
     */
    private int parseToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        log.debug("token: " + token);
        int userId = 0;
        if (ValidatorUtils.isNotBlank(token)) {
            String[] s = token.split("\\.");
            if (s.length == 2) {
                try {
                    //获取token中的userId
                    userId = Integer.parseInt(EncodeUtils.base64Encode(s[0]));
                    //取出redis中的token
                    String tokenCache = cacheUtils.getToken(userId);
                    //比较
                    if (!token.equals(tokenCache)) {
                        userId = 0;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } else {
            throw new CustomException(401, "请先登录", "token为空");
        }
        return userId;
    }

}
