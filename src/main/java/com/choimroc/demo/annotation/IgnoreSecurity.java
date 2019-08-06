package com.choimroc.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 是否忽略认证,默认检查token和teamId
 *
 * @author choimroc
 * @since 2019/3/10
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreSecurity {
    //是否只忽略teamId
    boolean onlyTeamId() default false;
}