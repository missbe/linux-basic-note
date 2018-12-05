package com.example.springboot.damno.chapter2.service;

import com.example.springboot.damno.chapter2.pojo.User;
import org.springframework.stereotype.Service;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-11-29 下午3:42
 *
 * @author: lyg
 * description:
 **/
@Service
public class UserService {
    public void printUser(User user) {
        System.out.println("id:" + user.getId());
        System.out.println("name:" + user.getUserName());
        System.out.println("note" + user.getNote());
    }
}
