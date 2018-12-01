##### 引入：用于增强现有类新功能

-->新功能接口及实现

```java
package com.example.springboot.chapter4.chapter4.aspect.service;

import com.example.springboot.chapter4.chapter4.aspect.pojo.User;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-12-1 下午8:33
 * @author: lyg
 * description:
 **/

public interface UserValidator {
    /**
     * 检测用户对象是否为空
     * @param user 被检测用户对象
     * @return 检测结果
     */
    boolean validate(User user);
}
package com.example.springboot.chapter4.chapter4.aspect.service.impl;

import com.example.springboot.chapter4.chapter4.aspect.pojo.User;
import com.example.springboot.chapter4.chapter4.aspect.service.UserValidator;
import org.springframework.stereotype.Service;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-12-1 下午8:35
 * @author: lyg
 * description:
 **/
@Service
public class UserValidatorImpl implements UserValidator {
    /**
     * 检测用户对象是否为空
     *
     * @param user 被检测用户对象
     * @return 检测结果
     */
    @Override
    public boolean validate(User user) {
        System.out.println("引入新的接口:" + UserValidator.class.getSimpleName());
        return user != null;
    }
}
```

--->Aspect

```java
package com.example.springboot.chapter4.chapter4.aspect;

import com.example.springboot.chapter4.chapter4.aspect.service.UserValidator;
import com.example.springboot.chapter4.chapter4.aspect.service.impl.UserValidatorImpl;
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

    /**
     * value:指向要增强功能的目标对象,这里对UserServiceImpl对象进行功能增强
     * defaultImpl:引入增强功能的类,这里采用UserValidatorImpl进行功能增强
     * UserService+代表其所有子类全都增强
     */
    @DeclareParents(
            value = "com.example.springboot.chapter4.chapter4.aspect.service.impl.UserServiceImpl",
            defaultImpl = UserValidatorImpl.class
    )
    public UserValidator userValidator;

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

-->Controller

```java
package com.example.springboot.chapter4.chapter4.aspect.controller;

import com.example.springboot.chapter4.chapter4.aspect.pojo.User;
import com.example.springboot.chapter4.chapter4.aspect.service.UserService;
import com.example.springboot.chapter4.chapter4.aspect.service.UserValidator;
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
    @GetMapping("/vp")
    public User validateAndPrint(String id,String userName,String note){
        User user = new User();
        user.setId(id);
        user.setUserName(userName);
        user.setNote(note);
        /*
          生成代理对象时，第二个参数传递所有接口数组，代理对象实现了所有的接口，
          所以可以调用接口里的方法和进行强制类型转换
          [Proxy.newProxyInstance(target.getClass().getClassLoader(),
                          target.getClass().getInterfaces(),proxyBean)]
         */
        UserValidator userValidator = (UserValidator)userService;
        if(userValidator.validate(user)){
            userService.printUser(user);
        }
        return user;
    }

}
```

-->Result

```java
/**
http://localhost:8080/vp?id=10085&userName=tt&note=note
//RESULT
http://localh引入新的接口:UserValidator
before.....
id:10085
userName:tt
note:note
after.....
afterReturning.....ost:8080/vp?id=10085&userName=tt&note=note
**/
```

