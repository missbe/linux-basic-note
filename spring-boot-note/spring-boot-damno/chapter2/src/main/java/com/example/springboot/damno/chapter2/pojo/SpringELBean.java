package com.example.springboot.damno.chapter2.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-11-30 上午11:03
 *
 * @author: lyg
 * description:
 **/
@Component
@ToString
@Setter
@Getter
public class SpringELBean {

    @Value("#{T(System).currentTimeMillis()}")
    private String nowTime;

    @Value("#{'hello world!'}")
    private String str;

    @Value("#{9.3E3}")
    private double d;

    @Value("#{3.14}")
    private float pi;

    /**
     * ?判断该属性是否为空，不为空执行方法
     */
    @Value("#{properties.url?.toUpperCase()}")
    private String benaProp;
}
