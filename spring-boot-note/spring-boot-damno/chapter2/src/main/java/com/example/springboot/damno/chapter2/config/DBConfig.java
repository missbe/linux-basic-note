package com.example.springboot.damno.chapter2.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-11-29 下午3:55
 *
 * @author: lyg
 * description:
 **/
@Configuration
@Slf4j
public class DBConfig {
    @Bean(name = "dataSource")
    @Conditional(DatabaseConditional.class)
    public DataSource getDataSource(@Value("${database.driver}")
                                    String driver,
                                    @Value("${database.url}")
                                    String url,
                                    @Value("${database.username}")
                                    String username,
                                    @Value("${database.password}")
                                    String password) {
        Properties props = new Properties();
        props.setProperty("driver", driver);
        props.setProperty("url", url);
        props.setProperty("username", username);
        props.setProperty("password", password);
        DataSource dataSource = null;
        try {
            dataSource = BasicDataSourceFactory.createDataSource(props);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return dataSource;
    }

}
