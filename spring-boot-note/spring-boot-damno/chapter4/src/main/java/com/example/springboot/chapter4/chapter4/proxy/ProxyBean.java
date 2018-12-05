package com.example.springboot.chapter4.chapter4.proxy;

import com.example.springboot.chapter4.chapter4.intercept.Interceptor;
import com.example.springboot.chapter4.chapter4.invoke.Invocation;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-11-30 上午11:42
 *
 * @author: lyg
 * description:
 **/
@Slf4j
public class ProxyBean implements InvocationHandler {

    private Object target = null;
    private Interceptor interceptor = null;

    /**
     * 绑定代理对象
     *
     * @param target      被代理对象
     * @param interceptor 拦截器
     * @return 代理对象
     */
    public static Object getProxyBean(Object target, Interceptor interceptor) {
        ProxyBean proxyBean = new ProxyBean();
        ///保存被代理对象
        proxyBean.target = target;
        ///保存拦截器
        proxyBean.interceptor = interceptor;
        ///返回生成的代理对象
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), proxyBean);
    }

    /**
     * 处理代理对象方法逻辑
     *
     * @param proxy  代理对象
     * @param method 当前方法
     * @param args   运行参数
     * @return 方法调用结果
     * @throws Throwable 异常
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ///异常标识
        boolean exceptionFlag = false;
        Invocation invocation = new Invocation(target, method, args);
        Object retObj = null;
        try {
            if (this.interceptor.before()) {
                retObj = this.interceptor.around(invocation);
            } else {
                retObj = method.invoke(target, args);
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            exceptionFlag = true;
        }
        this.interceptor.after();
        if (exceptionFlag) {

        }
        return null;
    }
}
