package com.choimroc.mybatisplusdemo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口颗粒度权限控制
 *
 * @author choimroc
 * @since 2019/5/4
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {
    /**
     * 接口所属的菜单的id
     */
    int menu() default 0;

    /**
     * 接口所属操作项id(按钮级)
     */
    int operation() default 0;
}
