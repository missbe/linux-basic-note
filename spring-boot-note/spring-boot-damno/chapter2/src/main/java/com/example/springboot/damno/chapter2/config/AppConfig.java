package com.example.springboot.damno.chapter2.config;

import com.example.springboot.damno.chapter2.pojo.User;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Service;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-11-29 下午3:28
 * @author: lyg
 * description:
 **/
@Configuration
@ComponentScan(basePackageClasses = {User.class}
        ,basePackages = {"com.example.springboot.damno.chapter2.*"}
        ,excludeFilters = {@ComponentScan.Filter(classes = {Service.class})}
        )
//@ImportResource(value = {"classpath:spring-mvc.xml"})
public class AppConfig {
}
