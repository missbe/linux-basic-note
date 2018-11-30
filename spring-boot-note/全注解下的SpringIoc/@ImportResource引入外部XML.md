```java
@Configuration
@ComponentScan(basePackageClasses = {User.class}
        ,basePackages = {"com.example.springboot.damno.chapter2.*"}
        ,excludeFilters = {@ComponentScan.Filter(classes = {Service.class})}
        )
///引入类路径下的XML文件
@ImportResource(value = {"classpath:spring-mvc.xml"})
public class AppConfig {
}
```

-->xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                        http://www.springframework.org/schema/beans/spring-beans-3.1.xsd
                        http://www.springframework.org/schema/context
                        http://www.springframework.org/schema/context/spring-context-3.1.xsd">
     <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource"
          destroy-method="close">
        <property name="driverClass" value="${driverClass}"/>
        <property name="jdbcUrl" value="${jdbcUrl}"/>
        <property name="user" value="${user}"/>
        <property name="password" value="${password}"/>

        <property name="minPoolSize" value="5"/>
        <property name="maxPoolSize" value="100"/>
        <property name="maxIdleTime" value="3600"/>
        <property name="acquireIncrement" value="2"/>
        <property name="maxStatements" value="0"/>
        <property name="initialPoolSize" value="2"/>
        <property name="idleConnectionTestPeriod" value="1800"/>
        <property name="acquireRetryAttempts" value="30"/>
        <property name="acquireRetryDelay" value="100"/>
        <property name="breakAfterAcquireFailure" value="false"/>
        <property name="testConnectionOnCheckout" value="false"/>
        <!-- <property name="connection.autoReconnect" value="true"/> <property
            name="connection.autoReconnectForPools" value="true"></property> <property
            name="connection.is-connection-validation-required" value="true" /> -->
    </bean>
</bean>
```

