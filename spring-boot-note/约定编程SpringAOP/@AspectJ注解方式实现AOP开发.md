-->Spring Aop流程约定

![流程约定](E:\cqupt\typora\study-basic-note\spring-boot-note\img\aop3.png)

### Spring Aop注解@Aspect实现

--User

```java
package com.example.springboot.chapter4.chapter4.aspect.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-12-1 下午7:44
 * @author: lyg
 * description:
 **/
@Setter
@Getter
@ToString
public class User {
    private String id;
    private String userName;
    private String note;
}
```

-->UserService UserServiceImpl

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
    void printUser(User user);
}
package com.example.springboot.chapter4.chapter4.aspect.service.impl;

import com.example.springboot.chapter4.chapter4.aspect.service.UserService;
import com.example.springboot.chapter4.chapter4.aspect.pojo.User;
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
    public void printUser(User user) {
        if(user == null){
            throw new RuntimeException("parameter can not null.");
        }
        System.out.println("id:" + user.getId());
        System.out.println("userName:" + user.getUserName());
        System.out.println("note:" + user.getNote());
    }
}
```

-->MyAspect

```java
package com.example.springboot.chapter4.chapter4.aspect;

import org.aspectj.lang.annotation.*;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-12-1 下午7:48
 * @author: lyg
 * description:
 **/
@Aspect
public class MyAspect {
    /**
     * execution:表示在执行的时候，拦截里面的正则匹配的方法，表示任意返回类型的方法。
     * com.*.service.impl.UserServiceImpl:指定目标对象的全限定名称
     * printUser:指定目标对象的方法
     * (..):表示任意参数进行匹配。
     */
    @Pointcut("execution(* com.example.springboot.chapter4.chapter4.aspect.service.impl.UserServiceImpl.printUser(..))")
    public  void pointCut(){}

    @Before("pointCut()")
    public void before(){
        System.out.println("before.....");
    }

    @After("pointCut()")
    public void after(){
        System.out.println("after.....");
    }

    @AfterReturning("pointCut()")
    public void afterReturning(){
        System.out.println("afterReturning.....");
    }

    @AfterThrowing("pointCut()")
    public void afterThrowing(){
        System.out.println("afterThrowing.....");
    }
}
```

-->SpringBoot

```java
package com.example.springboot.chapter4.chapter4;

import com.example.springboot.chapter4.chapter4.aspect.MyAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {
        "com.example.springboot.chapter4.chapter4.aspect"
})
public class Chapter4Application {

    @Bean(name = "myAspect")
    public MyAspect initMyAspect(){
        return new MyAspect();
    }

    public static void main(String[] args) {
        SpringApplication.run(Chapter4Application.class, args);
    }
}
```

-->Controller

```java
package com.example.springboot.chapter4.chapter4.aspect.controller;

import com.example.springboot.chapter4.chapter4.aspect.pojo.User;
import com.example.springboot.chapter4.chapter4.aspect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-12-1 下午8:09
 * @author: lyg
 * description:
 **/
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public User printUser(String id,String userName,String note){
        User user = new User();
        user.setId(id);
        user.setUserName(userName);
        user.setNote(note);
        userService.printUser(user);
        return user;
    }
}
```

-->Result

```java
GET: http://localhost:8080/user?id=10085&userName=tt&note=note
/**
before.....
id:10085
userName:tt
note:note
after.....
afterReturning.....
**/
```

### 引入

--> 当用别人提供的服务时需要增强功能时；