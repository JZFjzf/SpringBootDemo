package com.choimroc.demo.security;


import com.choimroc.demo.annotation.IgnoreSecurity;
import com.choimroc.demo.common.exception.CustomException;
import com.choimroc.demo.tool.EncodeUtils;
import com.choimroc.demo.tool.RedisUtils;
import com.choimroc.demo.tool.StringUtils;

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
    private final RedisUtils redisUtils;

    @Autowired
    public SecurityInterceptor(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
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

//        log.debug("requestIp: " + getIpAddress(request));
        log.debug("Method: " + method.getName() + ", IgnoreSecurity: " + method.isAnnotationPresent(IgnoreSecurity.class));
        log.debug("requestPath: " + requestPath);
        /* 判断接口是否需要认证*/
        IgnoreSecurity ignoreSecurity = method.getAnnotation(IgnoreSecurity.class);

        //不要验证
        if (ignoreSecurity != null && !ignoreSecurity.onlyTeamId()) {
            return true;
        }

        int userId = 0;
        int teamId = 0;
        //是否通过验证
        boolean pass = true;
        //如果没有注解则检查token和teamId
        if (ignoreSecurity == null) {
            userId = parseToken(request);
            teamId = parseTeamId(request);

            if (userId == 0 || teamId == 0) {
                pass = false;
            }

        } else if (ignoreSecurity.onlyTeamId()) {
            //如果有注解但是onlyTeamId为true, 则忽略TeamId的检查,只检查token
            userId = parseToken(request);
            if (userId == 0) {
                pass = false;
            }
        }

        if (pass) {
            //如果false一般就是key已经失效
            if (!redisUtils.refreshToken(userId)) {
                throw new CustomException(401, "身份认证失败,请重新登录");
            }

            //放入UserInfo
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userId);
            userInfo.setTeamId(teamId);

            request.setAttribute("CurrentUser", userInfo);
        } else {
            throw new CustomException(401, "身份认证失败,请重新登录");
        }

        return true;
    }


    /**
     * 解析token，获取userId
     */
    private int parseToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        log.debug("token: " + token);
        int userId = 0;
        if (!StringUtils.isEmpty(token)) {
            String[] s = token.split("\\.");
            if (s.length == 2) {
                try {
                    //获取token中的userId
                    userId = Integer.parseInt(EncodeUtils.base64Encode(s[0]));
                    //取出redis中的token
                    String tokenCache = redisUtils.getToken(userId);
                    //比较
                    if (!token.equals(tokenCache)) {
                        userId = 0;
//                        throw new RuntimeException("tokenCache is " + tokenCache);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return userId;
    }

    private int parseTeamId(HttpServletRequest request) {
        String strTeamId = request.getHeader("teamId");
        log.debug("teamId: " + strTeamId);
        int teamId = 0;
        if (!StringUtils.isEmpty(strTeamId)) {
            try {
                teamId = Integer.parseInt(strTeamId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return teamId;
    }
}
