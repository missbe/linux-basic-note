package com.example.springboot.chapter4.chapter4.invoke;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-11-30 上午11:30
 * @author: lyg
 * description:
 **/
@Setter
@Getter
@ToString
public class Invocation {
    private Object[] params;
    private Method method;
    private Object target;

    public Invocation(Object target, Method method, Object[] params) {
        this.params = params;
        this.method = method;
        this.target = target;
    }
    /**
     * 反射方法->以反射的形式调用原有方法
     */
    public Object proceed() throws InvocationTargetException,IllegalAccessException{
        return method.invoke(target,params);
    }
}
