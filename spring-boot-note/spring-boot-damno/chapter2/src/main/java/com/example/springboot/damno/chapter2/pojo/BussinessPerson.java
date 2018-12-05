package com.example.springboot.damno.chapter2.pojo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-11-29 下午7:22
 *
 * @author: lyg
 * description:
 **/
@Component
public class BussinessPerson implements Person, BeanNameAware, BeanFactoryAware,
        ApplicationContextAware, InitializingBean, DisposableBean {
    private User user;

    @Autowired
    @Qualifier("user")
    public void setUser(User user) {
        System.out.println("延迟依赖注入...");
        this.user = user;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("[" + this.getClass().getSimpleName() + "]调用BeanFactory.setBeanFactory");
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("[" + this.getClass().getSimpleName() + "]BeanNameAware.setBeanName");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("[" + this.getClass().getSimpleName() + "]InitializingBean.afterPropertiesSet");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("[" + this.getClass().getSimpleName() + "]ApplicationContext.setApplicationContext");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("[" + this.getClass().getSimpleName() + "]DisposableBean.destroy");

    }

    @PostConstruct
    public void init() {
        System.out.println("[" + this.getClass().getSimpleName() + "]@PostConstruct");

    }

    @PreDestroy
    public void destroyV2() {
        System.out.println("[" + this.getClass().getSimpleName() + "]@PreDestroy");
    }

    @Override
    public void service() {
        System.out.println(user.toString());
    }
}
