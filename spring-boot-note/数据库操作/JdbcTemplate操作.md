JdbcTemplate模板操作数据库

-->application.properties

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/springboot
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=www.missbe.cn
```

--->Entity实体

```java
package com.example.springboot.demo.chapter5.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-12-2 下午2:28
 * @author: lyg
 * description:
 **/
@Setter
@Getter
@ToString
public class User {
    private Long id = null;
    private String userName = null;
    private SexType sex = null;
    private String note = null;
}
package com.example.springboot.demo.chapter5.pojo;

import lombok.Getter;
import lombok.ToString;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-12-2 下午2:29
 * @author: lyg
 * description:性别枚举类型
 **/
@Getter
@ToString
public enum SexType {
    /**
     * 男性
     */
    MALE(1,"男"),
    /**
     * 女性
     */
    FEMALE(2,"女");

    private int id;
    private String name;

    SexType(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public static SexType getSexTypeById(int id){
        for(SexType sex : SexType.values()){
            if(sex.getId() == id){
                return sex;
            }
        }
        return null;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

-->@Service

```java
package com.example.springboot.demo.chapter5.service;

import com.example.springboot.demo.chapter5.pojo.User;

import java.util.List;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-12-2 下午2:35
 * @author: lyg
 * description:
 **/

public interface JdbcTmpUserService {
    User getUser(Long id);

    List<User> findUsers(String userName, String note);

    int insertUser(User user);

    int updateUser(User user);

    int deleteUser(Long id);

    User getUserByConnectionCallback(Long id);

    User getUserByStatementCallback(Long id);
}
package com.example.springboot.demo.chapter5.service.impl;

import com.example.springboot.demo.chapter5.pojo.SexType;
import com.example.springboot.demo.chapter5.pojo.User;
import com.example.springboot.demo.chapter5.service.JdbcTmpUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-12-2 下午2:37
 * @author: lyg
 * description:
 **/
@Service
public class JdbcTmpUserServiceImpl implements JdbcTmpUserService {
    /**
     * 每次执行一条SQL语句底层都会开启一条数据库连接
     */
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTmpUserServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<User> getUserMapper(){
        RowMapper<User> userRowMapper = (ResultSet rs,int rowNum) ->{
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUserName(rs.getString("user_name"));
            int sexId = rs.getInt("sex");
            SexType sexType = SexType.getSexTypeById(sexId);
            user.setSex(sexType);
            user.setNote(rs.getString("note"));
            return user;
        };
        return userRowMapper;
    }

    @Override
    public User getUser(Long id) {
        String sql = "select id,user_name,sex,note from t_user where id = ?";
        Object[] params = new Object[]{id};
        User user = jdbcTemplate.queryForObject(sql,params,getUserMapper());
        return user;
    }

    @Override
    public List<User> findUsers(String userName, String note) {
        String sql = "select id,user_name,sex,note from t_user" +
                " where user_name like concat('%',?,'%') " +
                " and note like concat('%',?,'%') ";
        Object[] params = new Object[]{userName,note};
        List<User> userList = jdbcTemplate.query(sql, params, getUserMapper());
        return userList;
    }

    @Override
    public int insertUser(User user) {
        String sql = "insert into t_user(user_name,sex,note) " +
                " values(?,?,?) ";
        return jdbcTemplate.update(sql, user.getUserName(), user.getSex().getId(), user.getNote());
    }

    @Override
    public int updateUser(User user) {
        String sql = "update t_user set user_name = ?,sex = ?,note = ? " +
                " where id = ?";
        return jdbcTemplate.update(sql, user.getUserName(), user.getSex().getId(), user.getNote());
    }

    @Override
    public int deleteUser(Long id) {
        String sql = "delete from t_user where id = ?";
        return jdbcTemplate.update(sql, id);
    }
    /**
     * 执行批处理方法->一次执行多条SQL语句的两种方法
     */
    public User getUserByStatementCallback(Long id){
        ///通过Lambda表达式使用StatementCallback
        User result = this.jdbcTemplate.execute((Statement stmt)->{
            String sqlCount = "select count(*) total from t_user where id=" + id;
            ResultSet resultSet = stmt.executeQuery(sqlCount);
            while ((resultSet.next())){
                int total = resultSet.getInt("total");
                System.out.println("total:"+total);
            }
            String sql = "select id,user_name,sex,note from t_user where id = "+id;
            ResultSet rs = stmt.executeQuery(sql);
            User user = null;
            while (rs.next()){
                int rowNum = rs.getRow();
                user = getUserMapper().mapRow(rs,rowNum);
            }
            return user;
        });
        return result;

    }
    public User getUserByConnectionCallback(Long id){
        return this.jdbcTemplate.execute((Connection conn)->{
            String sqlCount = "select count(*) total from t_user where id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlCount);
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                System.out.println("total->" + resultSet.getInt("total"));
            }
            String sql = "select id,user_name,sex,note from t_user where id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            User user = null;
            while (rs.next()){
                int rowNum = rs.getRow();
                user = getUserMapper().mapRow(rs, rowNum);
            }
            return user;
        });
    }
}
```

-->Test

```java
package com.example.springboot.demo.chapter5.service.impl;

import com.example.springboot.demo.chapter5.pojo.SexType;
import com.example.springboot.demo.chapter5.pojo.User;
import com.example.springboot.demo.chapter5.service.JdbcTmpUserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * mail: love1208tt@foxmail.com
 * Copyright (c) 2018. unnet.missbe
 * Date:  18-12-2 下午4:29
 * @author: lyg
 * description:
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JdbcTmpUserServiceImplTest {
    @Autowired
    private JdbcTmpUserService jdbcTmpUserService;

    private User user;
    @Before
    public void init(){
        user = new User();
        user.setSex(SexType.MALE);
        user.setUserName("unnnet");
        user.setNote("missbe");
    }

    @Test
    public void getUser() {
        System.out.println("---->Result:" + jdbcTmpUserService.getUser(1L));
    }

    @Test
    public void findUsers() {
        System.out.println("---->Result:" + jdbcTmpUserService.findUsers("unnnet","missbe"));
    }

    @Test
    public void insertUser() {
        System.out.println("---->Result:" + jdbcTmpUserService.insertUser(user));
    }

    @Test
    public void updateUser() {
        System.out.println("---->Result:" + jdbcTmpUserService.updateUser(user));
    }

    @Test
    public void deleteUser() {
        System.out.println("---->Result:" + jdbcTmpUserService.deleteUser(1L));
    }

    @Test
    public void getUserByStatementCallback() {
        System.out.println("---->Result:" + jdbcTmpUserService.getUserByStatementCallback(1L));
    }

    @Test
    public void getUserByConnectionCallback() {
        System.out.println("---->Result:" + jdbcTmpUserService.getUserByConnectionCallback(1L));
    }
}
```