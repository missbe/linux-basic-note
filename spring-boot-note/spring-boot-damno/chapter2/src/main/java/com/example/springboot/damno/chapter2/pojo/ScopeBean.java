package com.example.springboot.damno.chapter2.pojo;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-11-30 上午10:31
 *
 * @author: lyg
 * description:
 **/
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ScopeBean {

    @Override
    public String toString() {
        return "默认单例|配置";
    }
}
