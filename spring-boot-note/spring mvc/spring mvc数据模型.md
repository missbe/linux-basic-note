spring mvc数据模型设计图

![spring mvc数据模型设计图](E:\Typora\study-basic-note\spring-boot-note\img\spring mc数据模型.png)

> 在类ModelAndView中存在一个ModelMap类型的属性，继承了LinkedHashMap类，具备Map接口的一切特性，还可以增加数据属性。在spring mvc的应用中，如果在控制器方法的参数使用MoedlAndView、Model或者ModelMap作为参数类型，spring mvc会自动创建数据模型对象。
>
> ```java
> @RequestMapping("/data")
> @Controller
> public class DataModelController {
>     // 注入用户服务类
>     @Autowired
>     private UserService userService = null;
>     
>     // 测试Model接口
>     @GetMapping("/model")
>     public String useModel(Long id, Model model) {
>         User user = userService.getUser(id);
>         model.addAttribute("user", user);
>         // 这里返回字符串，在Spring MVC中，会自动创建ModelAndView且绑定名称
>         return "data/user";
>     }
>     
>     // 测试modelMap类
>     @GetMapping("/modelMap")
>     public ModelAndView useModelMap(Long id, ModelMap modelMap) {
>         User user = userService.getUser(id);
>         ModelAndView mv = new ModelAndView();
>         // 设置视图名称
>         mv.setViewName("data/user");
>         // 设置数据模型，此处modelMap并没有和mv绑定，这步系统会自动处理
>         modelMap.put("user", user);
>         return mv;
>     }
>     
>     // 测试ModelAndView
>     @GetMapping("/mav")
>     public ModelAndView useModelAndView(Long id, ModelAndView mv) {
>         User user = userService.getUser(id);
>         // 设置数据模型
>         mv.addObject("user", user);
>         // 设置视图名称
>         mv.setViewName("data/user");
>         return mv;
>     }
> }
> ```
>
> <!--user.jsp页面测试后端绑定的模型数据-->\
>
> ```jsp
> <%@ page language="java" contentType="text/html; charset=UTF-8"  
> pageEncoding="UTF-8"%>
> <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
> "http://www.w3.org/TR/html4/loose.dtd">
> <html>
> <head>
> <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
> <title>用户信息</title>
> </head>
> <body>
>     <table>
>         <tr>
>             <td>编号</td>
>             <td>${user.id}</td>
>         </tr>
>         <tr>
>             <td>用户名</td>
>             <td>${user.userName}</td>
>         </tr>
>         <tr>
>             <td>备注</td>
>             <td>${user.note}</td>
>         </tr>
>     </table>
> </body>
> </html>
> ```
>
> 