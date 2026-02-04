package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用于标识需要执行自动填充的接口
 */
@Target(ElementType.METHOD)//指定注解的使用范围, 表示此注解只能应用于方法上
@Retention(RetentionPolicy.RUNTIME)//指定注解的生命周期,RUNTIME 表示注解在运行时仍然存在
public @interface AutoFill {

    OperationType value();
}
