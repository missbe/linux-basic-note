### spring-aop自定义约定实现

-->实现依赖结构图

![spring aop](..\img\aop1.png)

-->约定流程图

![aop](..\img\aop2.png)

-->HelloService

```java
package com.example.springboot.chapter4.chapter4.service;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-11-30 上午11:23
 * @author: lyg
 * description:
 **/

public interface HelloService {
    void sayHello(String name);
}
```

-->HelloserviceImpl

```java
package com.example.springboot.chapter4.chapter4.service.impl;

import com.example.springboot.chapter4.chapter4.service.HelloService;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-11-30 上午11:23
 * @author: lyg
 * description:
 **/

public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello(String name) {
        if(name == null || name.trim().equals("")){
            throw new RuntimeException("paramter is null");
        }
        System.out.println("Hello " + name);
    }
}
```

-->Interceptor

```java
package com.example.springboot.chapter4.chapter4.intercept;


import com.example.springboot.chapter4.chapter4.invoke.Invocation;

import java.lang.reflect.InvocationTargetException;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-11-30 上午11:25
 * @author: lyg
 * description:
 **/

public interface Interceptor {
    /**
     * 事前方法
     */
    boolean before();

    /**
     * 事后方法
     */
    void after();

    /**
     *取代原有事件方法
     */
    Object around(Invocation invocation)
            throws InvocationTargetException,IllegalAccessException;

    /**
     *事后返回方法，事件没有发生异常执行
     */
    void afterReturning();

    /**
     *事后异常方法，事件发生异常后执行
     */
    void afterThrowing();

    /**
     *是否用around方法取代原有方法
     */
    boolean userAround();
}
```

-->MyInterceptor

```java
package com.example.springboot.chapter4.chapter4.invoke;

import com.example.springboot.chapter4.chapter4.intercept.Interceptor;

import java.lang.reflect.InvocationTargetException;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-11-30 上午11:33
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
     *
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
```

-->ProxyBean

```java
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
 * @author: lyg
 * description:
 **/
@Slf4j
public class ProxyBean implements InvocationHandler {

    private Object target = null;
    private Interceptor interceptor = null;

    /**
     * 绑定代理对象
     * @param target 被代理对象
     * @param interceptor 拦截器
     * @return 代理对象
     */
    public static Object getProxyBean(Object target, Interceptor interceptor){
        ProxyBean proxyBean = new ProxyBean();
        ///保存被代理对象
        proxyBean.target = target;
        ///保存拦截器
        proxyBean.interceptor = interceptor;
        ///返回生成的代理对象
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),proxyBean);
    }

    /**
     * 处理代理对象方法逻辑
     * @param proxy 代理对象
     * @param method 当前方法
     * @param args 运行参数
     * @return 方法调用结果
     * @throws Throwable 异常
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ///异常标识
        boolean exceptionFlag = false;
        Invocation invocation = new Invocation(target,method,args);
        Object retObj = null;
        try {
            if(this.interceptor.before()){
                retObj = this.interceptor.around(invocation);
            }else {
                retObj = method.invoke(target,args);
            }
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
            exceptionFlag = true;
        }
        this.interceptor.after();
        if(exceptionFlag){
            this.interceptor.afterThrowing();
        }else{
            this.interceptor.afterReturning();
            return retObj;
        }
        return null;
    }
}
```

-->ProxyBeanTest

```java
package com.example.springboot.chapter4.chapter4.proxy;

import com.example.springboot.chapter4.chapter4.invoke.MyInterceptor;
import com.example.springboot.chapter4.chapter4.service.HelloService;
import com.example.springboot.chapter4.chapter4.service.impl.HelloServiceImpl;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-12-1 上午9:47
 * @author: lyg
 * description:
 **/

public class ProxyBeanTest {

    @Test
    public void getProxyBean() {
        HelloService helloService = new HelloServiceImpl();
        HelloService proxy = (HelloService) ProxyBean.getProxyBean(helloService,new MyInterceptor());
        proxy.sayHello(" world!");
    }
}
///RESULT
/**
my_interceptor before....
my_interceptor around before....
Hello  world!
my_interceptor around after....
my_interceptor after....
my_interceptor afterReturning....
*/
```

