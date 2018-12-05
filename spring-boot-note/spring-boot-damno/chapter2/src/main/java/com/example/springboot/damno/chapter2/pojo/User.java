package com.example.springboot.damno.chapter2.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-11-29 下午3:26
 *
 * @author: lyg
 * description:
 **/
@Setter
@Getter
@ToString
@Component
public class User {
    private Long id = 1L;
    private String userName = "user_name";
    private String note = "note";
}
