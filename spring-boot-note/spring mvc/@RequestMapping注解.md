@RequestMapping<!--源码分析-->

```java 
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface RequestMapping {
    /*配置请求映射名称*/
    String name() default "";
    /*通过路径映射*/
    @AliasFor("path")
    String[] value() default {};
     /*通过路径映射加path配置项*/
    @AliasFor("value")java
    String[] path() default {};
     /*限定响应的Http请求，默认put,get,post等都响应*/
    RequestMethod[] method() default {};
     /*当存在对应的Http参数时才响应请求*/
    String[] params() default {};
     /*限定请求头存在对应的参数时才响应*/
    String[] headers() default {}; 
    /*限定http请求体提交类型，如：application/json*/
    String[] consumes() default {};
     /*限定返回的内容类型，仅当http请求头中包含指定类型时才返回*/
    String[] produces() default {};
}
```

> @GetMapping -> @RequestMapping(    method = {RequestMethod.GET})