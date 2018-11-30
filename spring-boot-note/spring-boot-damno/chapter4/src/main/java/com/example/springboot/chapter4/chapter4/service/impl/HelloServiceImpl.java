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
