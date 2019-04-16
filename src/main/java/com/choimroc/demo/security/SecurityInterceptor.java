package com.choimroc.demo.security;

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
public class SecurityInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String requestPath = request.getRequestURI();
//        log.debug("requestIp: " + getIpAddress(request));
        log.debug("Method: " + method.getName() + ", IgnoreSecurity: " + method.isAnnotationPresent(IgnoreSecurity.class));
        log.debug("requestPath: " + requestPath);
        if (requestPath.contains("/v2/api-docs") || requestPath.contains("/swagger") || requestPath.contains("/configuration/ui")) {
            return true;
        }
        if (requestPath.contains("/error")) {
            return true;
        }
        if (method.isAnnotationPresent(IgnoreSecurity.class)) {
            return true;
        }
        String token = request.getHeader("token");
        return super.preHandle(request, response, handler);
    }
}
