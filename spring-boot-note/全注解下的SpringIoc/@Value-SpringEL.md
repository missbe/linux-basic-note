SpringEL使用

```java
///${database.driver}占位符，读取上下文属性装配到属性当中
@Value("${database.driver}")
///#{...}代表启用spring表达式
///T(...)代表引入类，除默认加载包不需要类全限定名外；
////其它需要全限定类名，后面是调用的方法
@Value("#{T(System).currentTimeMillis()}")
private String nowTime;
```

-->SpringEL使用

```java
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
//TEST
 @Test
public void testSpringEL(){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        SpringELBean bean = ctx.getBean(SpringELBean.class);
        System.out.println(bean.toString());
}
//RESULT
SpringELBean(nowTime=1543547262325, str=hello world!, d=9300.0, pi=3.14, benaProp=jdbc:mysql://localhost:3306/springboot)
```

--->使用SpringEL进行计算

![springEL](E:\cqupt\typora\study-basic-note\spring-boot-note\img\springEL.png)