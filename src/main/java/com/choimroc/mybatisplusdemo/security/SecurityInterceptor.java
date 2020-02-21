package com.choimroc.mybatisplusdemo.security;


import com.choimroc.mybatisplusdemo.annotation.IgnoreSecurity;
import com.choimroc.mybatisplusdemo.annotation.Permission;
import com.choimroc.mybatisplusdemo.common.exception.CustomException;
import com.choimroc.mybatisplusdemo.tool.CacheUtils;
import com.choimroc.mybatisplusdemo.tool.EncodeUtils;
import com.choimroc.mybatisplusdemo.tool.ValidatorUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

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

        Long userId = checkToken(request);
        //如果refresh false一般就是key已经失效
        if (userId == null || !cacheUtils.refreshToken(userId)) {
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
     * 解析token,获取userId
     *
     * @return userId
     */
    private Long checkToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (ValidatorUtils.isBlank(token)) {
            throw new CustomException(401, "请先登录", "token为空");
        } else {
            String[] s = token.split("\\.");
            try {
                Long userId = Long.parseLong(EncodeUtils.base64Encode(s[0]));
                //取出redis中的token
                String tokenCache = cacheUtils.getToken(userId);
                //比较
                if (token.equals(tokenCache)) {
                    return userId;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    private boolean checkPermission(String permissionStr, int permissionId) {
        if (permissionId != 0) {
            List<String> permissionList = Arrays.asList(permissionStr.split(","));
            return permissionList.contains(String.valueOf(permissionId));
        }
        return true;
    }

}
