package com.example.springboot.damno.chapter2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-11-29 下午3:07
 *
 * @author: lyg
 * description:
 **/

@Controller
public class IndexController {
    @RequestMapping("/index")
    public String index() {
        System.out.println("/index");
        return "index";
    }
}
