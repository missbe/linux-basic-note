配置多个AOP切面，指定拦截顺序可以用@Order(1)注解或者实现Ordered接口；

-->Interface

```java
package com.example.springboot.chapter4.chapter4.aspect.service;

import com.example.springboot.chapter4.chapter4.aspect.pojo.User;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-12-1 下午7:43
 * @author: lyg
 * description:
 **/
public interface UserService {
    void manyAspect(String msg);
}
package com.example.springboot.chapter4.chapter4.aspect.service.impl;

import com.example.springboot.chapter4.chapter4.aspect.pojo.User;
import com.example.springboot.chapter4.chapter4.aspect.service.UserService;
import org.springframework.stereotype.Service;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-12-1 下午7:45
 * @author: lyg
 * description:
 **/
@Service
public class UserServiceImpl implements UserService {
    @Override
    public void manyAspect(String msg) {
        System.out.println(msg);
    }
}
```

--->AOP

```java
package com.example.springboot.chapter4.chapter4.aspect;

import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-12-2 上午11:08
 * @author: lyg
 * description:
 **/
@Aspect
@Order(3)
public class MyAspect1 {

    /**
     * execution:表示在执行的时候，拦截里面的正则匹配的方法，表示任意返回类型的方法。
     * com.*.service.impl.UserServiceImpl:指定目标对象的全限定名称
     * printUser:指定目标对象的方法
     * (..):表示任意参数进行匹配。
     */
    @Pointcut("execution(* com.example.springboot.chapter4.chapter4.aspect.service.impl.UserServiceImpl.manyAspect(..))")
    public  void manyAspect(){}

    @Before("manyAspect()")
    public void before(){
        System.out.println("MyAspect1 before.....");
    }

    @After("manyAspect()")
    public void after(){
        System.out.println("MyAspect1 after.....");
    }

    @AfterReturning("manyAspect()")
    public void afterReturning(){
        System.out.println("MyAspect1 afterReturning.....");
    }

    @AfterThrowing("manyAspect()")
    public void afterThrowing(){
        System.out.println("MyAspect1 afterThrowing.....");
    }

}
package com.example.springboot.chapter4.chapter4.aspect;

import org.aspectj.lang.annotation.*;
import org.springframework.core.Ordered;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-12-2 上午11:08
 * @author: lyg
 * description:
 **/
@Aspect
public class MyAspect2 implements Ordered {

    @Override
    public int getOrder() {
        return 2;
    }
    /**
     * execution:表示在执行的时候，拦截里面的正则匹配的方法，表示任意返回类型的方法。
     * com.*.service.impl.UserServiceImpl:指定目标对象的全限定名称
     * printUser:指定目标对象的方法
     * (..):表示任意参数进行匹配。
     */
    @Pointcut("execution(* com.example.springboot.chapter4.chapter4.aspect.service.impl.UserServiceImpl.manyAspect(..))")
    public  void manyAspect(){}

    @Before("manyAspect()")
    public void before(){
        System.out.println("MyAspect2 before.....");
    }

    @After("manyAspect()")
    public void after(){
        System.out.println("MyAspect2 after.....");
    }

    @AfterReturning("manyAspect()")
    public void afterReturning(){
        System.out.println("MyAspect2 afterReturning.....");
    }

    @AfterThrowing("manyAspect()")
    public void afterThrowing(){
        System.out.println("MyAspect2 afterThrowing.....");
    }
}
package com.example.springboot.chapter4.chapter4.aspect;

import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-12-2 上午11:08
 * @author: lyg
 * description:
 **/
@Aspect
@Order(1)
public class MyAspect3 {
    /**
     * execution:表示在执行的时候，拦截里面的正则匹配的方法，表示任意返回类型的方法。
     * com.*.service.impl.UserServiceImpl:指定目标对象的全限定名称
     * printUser:指定目标对象的方法
     * (..):表示任意参数进行匹配。
     */
    @Pointcut("execution(* com.example.springboot.chapter4.chapter4.aspect.service.impl.UserServiceImpl.manyAspect(..))")
    public  void manyAspect(){}

    @Before("manyAspect()")
    public void before(){
        System.out.println("MyAspect3 before.....");
    }

    @After("manyAspect()")
    public void after(){
        System.out.println("MyAspect3 after.....");
    }

    @AfterReturning("manyAspect()")
    public void afterReturning(){
        System.out.println("MyAspect3 afterReturning.....");
    }

    @AfterThrowing("manyAspect()")
    public void afterThrowing(){
        System.out.println("MyAspect3 afterThrowing.....");
    }
}
```

-->Bean

```java
package com.example.springboot.chapter4.chapter4;

import com.example.springboot.chapter4.chapter4.aspect.MyAspect;
import com.example.springboot.chapter4.chapter4.aspect.MyAspect1;
import com.example.springboot.chapter4.chapter4.aspect.MyAspect2;
import com.example.springboot.chapter4.chapter4.aspect.MyAspect3;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = {
        "com.example.springboot.chapter4.chapter4.aspect"
})
@EnableAspectJAutoProxy
public class Chapter4Application {
    /**
    Note:不用@Order指定顺序时，会使用配置Bean的顺序执行拦截
    **/
    @Bean(name = "myAspect2")
    public MyAspect2 initMyAspect2(){
        return new MyAspect2();
    }

    @Bean(name = "myAspect1")
    public MyAspect1 initMyAspect1(){
        return new MyAspect1();
    }

    @Bean(name = "myAspect3")
    public MyAspect3 initMyAspect3(){
        return new MyAspect3();
    }

    public static void main(String[] args) {
        SpringApplication.run(Chapter4Application.class, args);
    }
}
```

--->Result

```java
MyAspect3 before.....
MyAspect2 before.....
MyAspect1 before.....
many aspect...
MyAspect1 after.....
MyAspect1 afterReturning.....
MyAspect2 after.....
MyAspect2 afterReturning.....
MyAspect3 after.....
MyAspect3 afterReturning.....
```

