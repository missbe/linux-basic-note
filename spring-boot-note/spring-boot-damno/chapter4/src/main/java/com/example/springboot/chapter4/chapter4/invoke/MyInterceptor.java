package com.example.springboot.chapter4.chapter4.invoke;

import com.example.springboot.chapter4.chapter4.intercept.Interceptor;

import java.lang.reflect.InvocationTargetException;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-11-30 上午11:33
 *
 * @author: lyg
 * description:
 **/

public class MyInterceptor implements Interceptor {
    /**
     * 事前方法
     */
    @Override
    public boolean before() {
        System.out.println("my_interceptor before....");
        return true;
    }

    /**
     * 事后方法
     */
    @Override
    public void after() {
        System.out.println("my_interceptor after....");
    }

    /**
     * 取代原有事件方法
     */
    @Override
    public Object around(Invocation invocation) throws InvocationTargetException, IllegalAccessException {
        System.out.println("my_interceptor around before....");
        Object obj = invocation.proceed();
        System.out.println("my_interceptor around after....");
        return obj;
    }

    /**
     * 事后返回方法，事件没有发生异常执行
     */
    @Override
    public void afterReturning() {
        System.out.println("my_interceptor afterReturning....");
    }

    /**
     * 事后异常方法，事件发生异常后执行
     */
    @Override
    public void afterThrowing() {
        System.out.println("my_interceptor afterThrowing....");
    }

    /**
     * 是否用around方法取代原有方法
     */
    @Override
    public boolean userAround() {
        System.out.println("my_interceptor userAround....");
        return true;
    }
}
