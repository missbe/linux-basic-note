-->application.properties

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/springboot
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=www.missbe.cn
###JPA
spring.jpa.database=mysql
### 使用MySql数据库方言->使用InnoDB引擎
spring.jpa.database-platform=org.hibernate.dialect.MySQL5InnoDBDialect
### 打印sql
spring.jpa.show-sql=true
### DDL策略为update
spring.jpa.hibernate.ddl-auto=update

### mybatis
mybatis.type-handlers-package=com.example.springboot.demo.chapter5.typehandler
mybatis.type-aliases-package=com.example.springboot.demo.chapter5.pojo

### logging
logging.level.root=debug
logging.level.org.springframework=debug
logging.level.org.mybatis=debug
```

-->entity

```java
package com.example.springboot.demo.chapter5.pojo;

import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-12-2 下午2:28
 * @author: lyg
 * description:
 **/
@Alias("user") /*mybatis指定别名*/

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id = null;
    private String userName = null;
    /**枚举需要使用typeHandler进行转换*/
    private SexType sex = null;
    private String note = null;
}
```

---typeHander(针对枚举类型进行处理)

```java
package com.example.springboot.demo.chapter5.typehandler;

import com.example.springboot.demo.chapter5.pojo.SexType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-12-2 下午8:56
 * @author: lyg
 * description:
 **/
@MappedJdbcTypes(JdbcType.INTEGER) /*JdbcType*/
@MappedTypes(value = SexType.class)/*JavaType*/

public class SexTypeHandler extends BaseTypeHandler<SexType> {
    /**设置非空性别参数*/
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, SexType sexType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i,sexType.getId());
    }

    /**通过列名读取性别*/
    @Override
    public SexType getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int sex = resultSet.getInt(s);
        if(sex != 1 && sex != 2){
            return null;
        }
        return SexType.getSexTypeById(sex);
    }

    /**通过下标读取性别*/
    @Override
    public SexType getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int sex = resultSet.getInt(i);
        if(sex != 1 && sex != 2){
            return null;
        }
        return SexType.getSexTypeById(sex);
    }

    /**通过存储过程读取性别*/
    @Override
    public SexType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int sex = callableStatement.getInt(i);
        if(sex != 1 && sex != 2){
            return null;
        }
        return SexType.getSexTypeById(sex);
    }
}
```

-->config

```java
package com.example.springboot.demo.chapter5.config;

import com.example.springboot.demo.chapter5.dao.MyBatisUserDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-12-2 下午9:02
 * @author: lyg
 * description:
 **/
@Configuration
public class MybatisConfig {
    private final SqlSessionFactory sessionFactory;

    @Autowired
    public MybatisConfig(SqlSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**方式一：手动装配DAO*/
    @Bean
    public MapperFactoryBean<MyBatisUserDao> initMybatisUserDao(){
        MapperFactoryBean<MyBatisUserDao> bean = new MapperFactoryBean<>();
        bean.setMapperInterface(MyBatisUserDao.class);
        bean.setSqlSessionFactory(sessionFactory);
        return bean;
    }

    /**方式二：扫描包里指定注解*/
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        ///加载sqlSessionFactory实例,spring boot自动生产
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        ///定义扫描的包
        mapperScannerConfigurer.setBasePackage("com.example.springboot.demo.chapter5.dao");
        ///设置标注@Repository注解的接口才扫描
        mapperScannerConfigurer.setAnnotationClass(Repository.class);
        return mapperScannerConfigurer;
    }
}
/*方式三->在Chapter5Application中配置*/
@SpringBootApplication(scanBasePackages = {"com.example.springboot.demo.chapter5"})
@EnableJpaRepositories(
        basePackages = {"com.example.springboot.demo.chapter5.dao"}/*开启JPA编程-JPA接口扫描包路径*/
        )
@EntityScan(
        basePackages = {"com.example.springboot.demo.chapter5.pojo"}/*定义实体Bean扫描包路径*/
)
@MapperScan(
        /*指定扫描包*/
        basePackages = {"com.example.springboot.demo.chapter5.*"},
        /*指定sqlSessionFactory，如果sqlSessionTemplate被指定，则作废*/
        sqlSessionFactoryRef = "sqlSessionFactory",
        /*指定sqlSessionTemplate,将忽略sqlSessionFactory的配置*/
        sqlSessionTemplateRef = "sqlSessionTemplate",
        /*指定扫描的接口类型*/
        annotationClass = Repository.class
)
public class Chapter5Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter5Application.class, args);
    }
}
```

-->dao

```java
package com.example.springboot.demo.chapter5.dao;

import com.example.springboot.demo.chapter5.pojo.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-12-2 下午8:49
 * @author: lyg
 * description:
 **/
@Repository
public interface MyBatisUserDao {
    @Select("select id,user_name as userName,sex,note from t_user where id=#{0}")
    User getUser(Long id);
}
```

--test

```java
package com.example.springboot.demo.chapter5.dao;

import com.example.springboot.demo.chapter5.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-12-2 下午8:52
 * @author: lyg
 * description:
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MyBatisUserDaoTest {
    @Autowired
    private MyBatisUserDao myBatisUserDao;

    @Test
    public void getUser() {
        User user = myBatisUserDao.getUser(1L);
        System.out.println(user);
    }
}
//result
User(id=1, userName=unnnet, sex=SexType(id=2, name=女), note=missbe)
```



