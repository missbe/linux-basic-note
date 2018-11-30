springboot属性文件装配

--->jdbc.properties

```properties
#
# mail: love1208tt@foxmail.com
# Copyright (c) 2018. unnet.missbe
# Date:  18-11-29 下午8:43
# @author: lyg
# description:
#
database.driver=com.mysql.jdbc.Driver
database.url=jdbc:mysql://localhost:3306/springboot
database.password=123456
database.username=root
```

--->加载属性文件的组件

```java
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
```

--->日志

```java
BeanPostProcessor调用postProcessBeforeInitialization方法，参数Properties-->properties
BeanPostProcessor调用postProcessAfterInitialization方法，参数Properties-->properties
20:44:34.382 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Finished creating instance of bean 'properties'
20:44:34.382 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Returning cached instance of singleton bean 'user'
20:44:34.382 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'userService'
20:44:34.382 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating instance of bean 'userService'
20:44:34.382 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Eagerly caching bean 'userService' to allow for resolving potential circular references
BeanPostProcessor调用postProcessBeforeInitialization方法，参数UserService-->userService
BeanPostProcessor调用postProcessAfterInitialization方法，参数UserService-->userService
20:44:34.383 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Finished creating instance of bean 'userService'
20:44:34.384 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Returning cached instance of singleton bean 'org.springframework.context.event.internalEventListenerFactory'
20:44:34.407 [main] DEBUG org.springframework.context.annotation.AnnotationConfigApplicationContext - Unable to locate LifecycleProcessor with name 'lifecycleProcessor': using default [org.springframework.context.support.DefaultLifecycleProcessor@d35dea7]
20:44:34.407 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Returning cached instance of singleton bean 'lifecycleProcessor'
20:44:34.409 [main] DEBUG org.springframework.core.env.PropertySourcesPropertyResolver - Could not find key 'spring.liveBeansView.mbeanDomain' in any property source
20:44:34.411 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Returning cached instance of singleton bean 'properties'
Properties(driver=com.mysql.jdbc.Driver, url=jdbc:mysql://localhost:3306/springboot, username=root, password=123456)

```

