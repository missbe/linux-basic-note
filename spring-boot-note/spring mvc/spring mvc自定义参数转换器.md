><!--HttpMessageConverter接口-->
>
>```java
>public interface HttpMessageConverter<T> {
>    /*是否可读，var1为Java类型，var2为Http请求*/
>    boolean canRead(Class<?> var1, @Nullable MediaType var2);
>     /*判断var1是否能够转换var2媒体类型*/
>    boolean canWrite(Class<?> var1, @Nullable MediaType var2);
>     /*支持的媒体列表*/
>    List<MediaType> getSupportedMediaTypes();
>     /*当canRead验证通过后，读入Http请求信息*/
>    T read(Class<? extends T> var1, HttpInputMessage var2) throws IOException, HttpMessageNotReadableException;
>     /*当canWrite验证通过后，写入响应*/
>    void write(T var1, @Nullable MediaType var2, HttpOutputMessage var3) throws IOException, HttpMessageNotWritableException;
>}
>```
>
>控制器采用@RequestBody注解标注 ，处理器会采用请求体的内容进行参数转换，前面请求体为Json类型，所以首先会调用canRead方法来确定请求体是否可读，如果可以就调用read方法。
>
>HttpMessageConverter接口是将Http请求体转换为对应的Java对象，对于其它Http参数和其它内容，例如性别前端传递是一个整数，后端是一个枚举，这样就需要自定义的参数转换规则。

spring mvc处理http请求转换流程图 

![](F:\CODE\typora\study-basic-note\spring-boot-note\img\springmvc处理请求流程.png)

>Converter：普通转换器，将Http对应字符串转换为整型等
>
>Formatter：格式化转换器，日期字符串按照约定格式转换的
>
>GenericConverter：将Http参数转换为数组
>
>ConversionService：spring  mvc提供的服务机制管理数据类型转换
>
><!--ConversionService转化机制设计-->
>
>![](F:\CODE\typora\study-basic-note\spring-boot-note\img\ConversonService转化机制.png)

Converter接口实现一对一转换

```java
package com.springboot.chapter10.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.springboot.chapter10.pojo.User;

/**** imports ****/
/**
 * 自定义字符串用户转换器
 * Converter<String, User> String为源，User为目的
 */
@Component
public class StringToUserConverter implements Converter<String, User> {
    /**
     * 转换方法
     */
    @Override
    public User convert(String userStr) {
        ///转换源字符串 id-userName-note 到User对象
        User user = new User();
        String []strArr = userStr.split("-");
        Long id = Long.parseLong(strArr[0]);
        String userName = strArr[1];
        String note = strArr[2];
        user.setId(id);
        user.setUserName(userName);
        user.setNote(note);
        return user;
    }
}
```

