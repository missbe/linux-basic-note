JPA接口结构图

![JPA](E:\cqupt\typora\study-basic-note\spring-boot-note\img\jpa1.png)

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
```

-->@Entity

```java
package com.example.springboot.demo.chapter5.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-12-2 下午4:56
 * @author: lyg
 * description:
 **/
@Setter
@Getter
@ToString
@Entity(name = "userEntity") /*标明是一个实体类*/
@Table(name = "t_user_entity") /*定义映射的表*/
public class UserEntity {
    /**
     * 主键策略->自动递增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 定义属性和表的映射关系
     */
    @Column(name = "user_name")
    private String userName;

    private String note;
    /**
     * 定义转换器
     */
    @Convert(converter = SexConverter.class)
    private SexType sex;
}
package com.example.springboot.demo.chapter5.pojo;

import javax.persistence.AttributeConverter;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-12-2 下午5:00
 * @author: lyg
 * description: 性别转换器
 **/

public class SexConverter implements AttributeConverter<SexType,Integer> {
    @Override
    public Integer convertToDatabaseColumn(SexType sexType) {
        return sexType.getId();
    }

    @Override
    public SexType convertToEntityAttribute(Integer integer) {
        return SexType.getSexTypeById(integer);
    }
}
```

-->@Dao

```java
package com.example.springboot.demo.chapter5.dao;

import com.example.springboot.demo.chapter5.pojo.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-12-2 下午5:06
 * @author: lyg
 * description:
 **/

public interface JpaUserRepository extends JpaRepository<UserEntity,Long> {
}
```

-->@Controller

```java
package com.example.springboot.demo.chapter5.controller;

import com.example.springboot.demo.chapter5.dao.JpaUserRepository;
import com.example.springboot.demo.chapter5.pojo.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-12-2 下午5:07
 * @author: lyg
 * description:
 **/

@RestController
/*
@RestController("/jpa")不成功，@RestController只说明返回数据是JSON格式等
必须要写成->@RestController
@RequestMapping(value = "/jpa")才可以.
*/

@RequestMapping(value = "/jpa")
public class JpaController {
    private final JpaUserRepository jpaUserRepository;

    @Autowired
    public JpaController(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @GetMapping("/")
    public List<UserEntity> index(){
        return jpaUserRepository.findAll();
    }

    @GetMapping("/getUser")
    public UserEntity getUser(Long id){
        return jpaUserRepository.findById(id).get();
    }

}
```

