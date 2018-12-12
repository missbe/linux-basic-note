@Transactional注解->用于类上表示类所有公共非静态方法都将启用事务功能.

![spring事务约定](E:\cqupt\typora\study-basic-note\spring-boot-note\img\transactional.png)

-->@Transactional使用

```java
@Transactional/*开启事务*/
    public boolean updateLink(String appLinkName, String appLinkAddress, int appLinkFlag, String appDesc, String appOldName, String appDown, String appIndex, int defaultFlag) {
        return appLinkMapper.updateLink(appLinkName, appLinkAddress, appLinkFlag, appDesc, appOldName, appDown, appIndex, defaultFlag) > 0;
    }
```

-->@Transactional源码

```java
package org.springframework.transaction.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Transactional {
    /*通过bean name指定事务管理器*/
    @AliasFor("transactionManager")
    String value() default "";
 	/*同value属性*/
    @AliasFor("value")
    String transactionManager() default "";
     /*指定传播行为*/
    Propagation propagation() default Propagation.REQUIRED;
     /*指定隔离级别*/
    Isolation isolation() default Isolation.DEFAULT;
     /*指定超时时间(秒)*/
    int timeout() default -1;
     /*是否只读事务*/
    boolean readOnly() default false;
     /*方法在发生指定异常时回滚，默认所有异常都回滚*/
    Class<? extends Throwable>[] rollbackFor() default {};
     /*方法在发生指定异常名称时回滚，默认是所有异常都回滚*/
    String[] rollbackForClassName() default {};
     /*方法在发生指定异常时不回滚，默认是所有异常都回滚*/
    Class<? extends Throwable>[] noRollbackFor() default {};
     /*方法在发生指定异常名称时不回滚，默认是所有异常都回滚*/
    String[] noRollbackForClassName() default {};
}
```

隔离级别：

![事务隔离级别](E:\cqupt\typora\study-basic-note\spring-boot-note\img\isolation.png)

-->apllication.properties设置数据源的默认隔离级别

![默认隔离级别](E:\cqupt\typora\study-basic-note\spring-boot-note\img\default_isolation.png)

传播行为：在一个批量执行任务的过程中，调用 多个交易时，如果有一些交易发生异常，只是回滚哪些出现异常的交易，而不整个批量任务。

![传播行为](E:\cqupt\typora\study-basic-note\spring-boot-note\img\progagtion.png)

-->TransactionDefinition

```java
public interface TransactionDefinition {
    /*需要事务，默认传播行为，如果当前存在事务就用当前事务，否则新建一个事务运行子方法*/
    int PROPAGATION_REQUIRED = 0;
     /*支持事务，如果当前存在事务就沿用当前事务;如果不存在，则继续采用无事务的方式运行子方法*/
    int PROPAGATION_SUPPORTS = 1;
     /*必须使用事务，如果当前没有事务，则会抛出异常;如果存在当前事务，就沿用当前事务*/
    int PROPAGATION_MANDATORY = 2;
    /*无论当前事务是否存在，都会创建新事务运行方法，这样新事务就可以拥有新的锁和隔离级别等特征*/
    int PROPAGATION_REQUIRES_NEW = 3;
    /*不支持事务，当前存在事务，将挂起事务，运行方法*/
    int PROPAGATION_NOT_SUPPORTED = 4;
    /*不支持事务，如果当前存在事务，则抛出异常，否则使用无事务机制运行*/
    int PROPAGATION_NEVER = 5;
    /*在当前方法调用子方法时，如果子方法发生异常，只回滚子方法执行过的SQL，不回滚当前方法的事务*/
    int PROPAGATION_NESTED = 6;
    int ISOLATION_DEFAULT = -1;
    int ISOLATION_READ_UNCOMMITTED = 1;
    int ISOLATION_READ_COMMITTED = 2;
    int ISOLATION_REPEATABLE_READ = 4;
    int ISOLATION_SERIALIZABLE = 8;
    int TIMEOUT_DEFAULT = -1;

    int getPropagationBehavior();

    int getIsolationLevel();

    int getTimeout();

    boolean isReadOnly();

    @Nullable
    String getName();
}
public enum Propagation {
    REQUIRED(0),
    SUPPORTS(1),
    MANDATORY(2),
    REQUIRES_NEW(3),
    NOT_SUPPORTED(4),
    NEVER(5),
    NESTED(6);

    private final int value;

    private Propagation(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
```

