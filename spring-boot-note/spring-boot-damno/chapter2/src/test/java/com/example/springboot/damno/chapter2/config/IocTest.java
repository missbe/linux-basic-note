package com.example.springboot.damno.chapter2.config;

import com.example.springboot.damno.chapter2.pojo.Properties;
import com.example.springboot.damno.chapter2.pojo.ScopeBean;
import com.example.springboot.damno.chapter2.pojo.SpringELBean;
import com.example.springboot.damno.chapter2.pojo.User;
import com.example.springboot.damno.chapter2.service.UserService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-11-29 下午3:30
 * @author: lyg
 * description:
 **/

public class IocTest {
    private static Logger logger = LoggerFactory.getLogger(IocTest.class);

    @Test
    public void initUser() {
        logger.info("init user...");
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        User user = ctx.getBean(User.class);
        logger.info(user.toString());
        UserService userService = ctx.getBean(UserService.class);
        if(userService != null){
            userService.printUser(user);
        }
        System.out.println();
    }

    @Test
    public void testProperties(){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println(ctx.getBean(Properties.class));
    }

    @Test
    public void testAnnotation(){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        ctx.close();
    }

    @Test
    public void testScope(){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        ScopeBean scopeBean_one = ctx.getBean(ScopeBean.class);
        ScopeBean scopeBean_two = ctx.getBean(ScopeBean.class);
        System.out.println("scopeBean_one == scopeBean_two -->" + (scopeBean_one == scopeBean_two));
        System.out.println(scopeBean_one);
    }

    @Test
    public void testSpringEL(){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        SpringELBean bean = ctx.getBean(SpringELBean.class);
        System.out.println(bean.toString());
    }
}