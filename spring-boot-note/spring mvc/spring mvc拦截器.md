spring mvc拦截器

> 所有的拦截器都需要实现HandlerInterceptor接口，HandlerInterceptor源码如下：
>
> ```java
> public interface HandlerInterceptor {
>     /*处理器执行前方法*/
>     default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
>         return true;
>     }
>     /*处理器处理后方法*/
>     default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
>     }
>     /*处理器完成后方法*/
>     default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
>     }
> }
> ```
>
> <!--拦截器执行过程-->
>
> ![](E:\Typora\study-basic-note\spring-boot-note\img\拦截器执行过程.png)

开发自己的拦截器

```java
/*自定义拦截器逻辑*/
package com.springboot.chapter10.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class Interceptor1 implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, 
            HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("处理器前方法");
        // 返回true，不会拦截后续的处理
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, 
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        System.out.println("处理器后方法");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, 
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println("处理器完成方法");
    }
}
/*注册拦截器*/
@SpringBootApplication(scanBasePackages = "com.springboot.chapter10")
@MapperScan(basePackages = "com.springboot.chapter10", annotationClass = Mapper.class)
public class Chapter10Application implements WebMvcConfigurer {
    @Override
	 public void addInterceptors(InterceptorRegistry registry) {
	  // 注册拦截器到Spring MVC机制，然后它会返回一个拦截器注册
	  InterceptorRegistration ir = registry.addInterceptor(new Interceptor1());
	  // 指定拦截匹配模式，限制拦截器拦截请求
	  ir.addPathPatterns("/interceptor/*");
     }
}
/*拦截器控制器*/
@Controller
@RequestMapping("/interceptor")
public class InterceptorController {
	@GetMapping("/start")
	public String start() {
		System.out.println("执行处理器逻辑");
		return "/welcome";
	}
}
```

<!---视图渲染jsp页面-->

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>深入Spring MVC</title>
</head>
<body>
    <h1><%
    System.out.println("视图渲染");
    out.print("欢迎学习Spring Boot MVC章节\n");
    %></h1>
</body>
</html>
```

<!---执行结果-->

处理前方法

执行处理器逻辑...

处理器后方法...

视图渲染...

处理器完成方法...

NOTE:多个拦截器采用责任链模式的规则，处理前方法采用先注册先执行，处理后方法和完成后方法则是先注册后执行的规则。处理前方法先注册先执行，一旦返回false后续的拦截器、处理器和所有拦截器的处理后方法都不会被执行，相当于链了前面断了后面就没有必要执行了。