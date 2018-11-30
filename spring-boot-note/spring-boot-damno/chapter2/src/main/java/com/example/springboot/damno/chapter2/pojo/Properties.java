package com.example.springboot.damno.chapter2.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-11-29 下午8:29
 * @author: lyg
 * description:
 **/
@Component
@Getter
@Setter
@ToString
@PropertySource(value = {"classpath:jdbc.properties"},ignoreResourceNotFound = true)
public class Properties {
    @Value("${database.driver}")
    private String driver = "com.mysql.jdbc.Driver";
    @Value("${database.url}")
    private String url = "jdbc:mysql://localhost:3306/springboot";
    private String username = "root";
    private String password = "www.missbe.cn";


    @Value("${database.username}")
    public void setUsername(String username) {
        this.username = username;
    }

    @Value("${database.password}")
    public void setPassword(String password) {
        this.password = password;
    }
}
