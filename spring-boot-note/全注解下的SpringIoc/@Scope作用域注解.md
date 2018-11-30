@Scope注解作用

-->默认单例

```java
package com.example.springboot.damno.chapter2.pojo;
import org.springframework.stereotype.Component;
/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-11-30 上午10:31
 * @author: lyg
 * description:
 **/
@Component
public class ScopeBean {

    @Override
    public String toString() {
        return "默认单例|配置";
    }
}
///TEST
 @Test
    public void testScope(){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        ScopeBean scopeBean_one = ctx.getBean(ScopeBean.class);
        ScopeBean scopeBean_two = ctx.getBean(ScopeBean.class);
        System.out.println("scopeBean_one == scopeBean_two -->" + (scopeBean_one == scopeBean_two));
        System.out.println(scopeBean_one);
    }
///RESULT
///scopeBean_one == scopeBean_two -->true
///默认单例|配置
```

-->配置原生

```java
package com.example.springboot.damno.chapter2.pojo;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-11-30 上午10:31
 * @author: lyg
 * description:
 **/
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ScopeBean {

    @Override
    public String toString() {
        return "默认单例|配置";
    }
}
///RESULT
///scopeBean_one == scopeBean_two -->false
///默认单例|配置
```

