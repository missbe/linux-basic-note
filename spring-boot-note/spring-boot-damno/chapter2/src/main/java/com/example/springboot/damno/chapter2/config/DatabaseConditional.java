package com.example.springboot.damno.chapter2.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-11-29 下午9:17
 * @author: lyg
 * description:
 **/

public class DatabaseConditional implements Condition {
    /**
     * 数据库装配条件
     * @param conditionContext 条件上下文
     * @param annotatedTypeMetadata 注释类型的元数据
     * @return 是否装配Bean
     */
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        //取出环境变量
        Environment env = conditionContext.getEnvironment();
        ///判断属性文件是否对应存在配置
        return env.containsProperty("database.driver")
                && env.containsProperty("database.url")
                && env.containsProperty("database.username")
                && env.containsProperty("database.password");
    }
}
