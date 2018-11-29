@Autowired 自动注入 (by type)

@Autowired 提供的规则是：首先根据类型找到对应的Bean，如果对应的Bean不是唯一的，那么它会根据属性名称和Bean的名称进行匹配，如果能够匹配上，就使用该Bean，如果无法匹配，抛出异常。

@Autowired(required = false) ->不能确定一定存在并且允许被标注属性为null；

@Primary -> 修改优先权限。如果有两个同一类型的，优先使用带@Primary注解的类对象。

```java
@Setter
@Getter
@ToString
@Component
@Primary
public class User {
    private Long id = 1L;
    private String userName = "user_name";
    private String note = "note";
}
```

@Quelifier ->当多个类都带有@Primary注解时不能区分使用那个类对象。使用@Quelifier增加别名，通过类型和名称来确定使用那个类对象；

@Resource是JDK提供的注解，默认就是按照byName的方式寻找bean，一般一个name对应一个bean，当找不到与名称匹配的bean才会按照类型装配（byType）。 