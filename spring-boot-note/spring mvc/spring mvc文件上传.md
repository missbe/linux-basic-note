spring mvc对文件上传的支持

> 首先DispatcherServlet会使用适配器模式将HttpServletRequest接口对象转换为MultipartHttpServletRequest对象，该对象定义了操作文件的方法，实现文件上传。
>
> <!--文件请求转换类之间的关系-->
>
> ![](E:\Typora\study-basic-note\spring-boot-note\img\文件请求转换类之间关系.png)
>
> <!--MultipartResolver文件解析器，解析文件-->
>
> ![](E:\Typora\study-basic-note\spring-boot-note\img\spring mvc文件上传解析器.png)

spring boot中application.properties中配置文件上传

![](E:\Typora\study-basic-note\spring-boot-note\img\springboot文件上传配置.png)

spring boot开发文件上传功能

> <!--application.properties属性配置-->
>
> ```properties
> spring.mvc.view.prefix=/WEB-INF/jsp/
> spring.mvc.view.suffix=.jsp
> spring.mvc.date-format=yyyy-MM-dd
> 
> 
> # \u6307\u5B9A\u9ED8\u8BA4\u4E0A\u4F20\u7684\u6587\u4EF6\u5939
> spring.servlet.multipart.location=e:/springboot
> # \u9650\u5236\u5355\u4E2A\u6587\u4EF6\u6700\u5927\u5927\u5C0F\uFF0C\u8FD9\u91CC\u8BBE\u7F6E\u4E3A5M
> spring.servlet.multipart.max-file-size=5242880
> # \u9650\u5236\u6240\u6709\u6587\u4EF6\u6700\u5927\u5927\u5C0F,\u8FD9\u91CC\u8BBE\u7F6E\u4E3A20M
> spring.servlet.multipart.max-request-size=20MB 
> ```
>
> <!--upload.jsp上传文件页面-->
>
> ```jsp
> <%@ page language="java" contentType="text/html; charset=UTF-8"
>     pageEncoding="UTF-8"%>
> <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
> "http://www.w3.org/TR/html4/loose.dtd">
> <html>
> <head>
> <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
> <title>文件上传</title>
> </head>
>     <body>
>         <form method="post" 
>                 action="./part" enctype="multipart/form-data">
>             <input type="file" name="file" value="请选择上传的文件" /> 
>             <input type="submit" value="提交" />
>         </form>
>     </body>
> </html>
> ```
>
> <!--文件上传控制器-->
>
> ```java
> package com.springboot.chapter10.controller;
> 
> import java.io.File;
> import java.util.HashMap;
> import java.util.Map;
> import javax.servlet.http.HttpServletRequest;
> import javax.servlet.http.Part;
> import org.springframework.stereotype.Controller;
> import org.springframework.web.bind.annotation.GetMapping;
> import org.springframework.web.bind.annotation.PostMapping;
> import org.springframework.web.bind.annotation.RequestMapping;
> import org.springframework.web.bind.annotation.ResponseBody;
> import org.springframework.web.multipart.MultipartFile;
> import org.springframework.web.multipart.MultipartHttpServletRequest;
> 
> @Controller
> @RequestMapping("/file")
> public class FileController {
>     /**
>      * 打开文件上传请求页面
>      * @return 指向JSP的字符串
>      */
>     @GetMapping("/upload/page")
>     public String uploadPage() {
>         return "/file/upload";
>     }
>     
>     // 使用HttpServletRequest作为参数
>     @PostMapping("/upload/request")
>     @ResponseBody
>     public Map<String, Object> uploadRequest(HttpServletRequest request) {
>         boolean flag = false;
>         MultipartHttpServletRequest mreq = null;
>         // 强制转换为MultipartHttpServletRequest接口对象
>         if (request instanceof MultipartHttpServletRequest) {
>             mreq = (MultipartHttpServletRequest) request;
>         } else {
>             return dealResultMap(false, "上传失败");
>         }
>         // 获取MultipartFile文件信息
>         MultipartFile mf = mreq.getFile("file");
>         // 获取源文件名称
>         String fileName = mf.getOriginalFilename();
>         File file = new File(fileName);
>         try {
>             // 保存文件
>             mf.transferTo(file);
>         } catch (Exception e) {
>             e.printStackTrace();
>             return dealResultMap(false, "上传失败");
>         } 
>         return dealResultMap(true, "上传成功");
>     }
>     
>     // 使用Spring MVC的MultipartFile类作为参数
>     @PostMapping("/upload/multipart")
>     @ResponseBody
>     public Map<String, Object> uploadMultipartFile(MultipartFile file) {
>         String fileName = file.getOriginalFilename();
>         File dest = new File(fileName);
>         try {
>             file.transferTo(dest);
>         } catch (Exception e) {
>             e.printStackTrace();
>             return dealResultMap(false, "上传失败");
>         } 
>         return dealResultMap(true, "上传成功");
>     }
>     
>     @PostMapping("/upload/part")
>     @ResponseBody
>     public Map<String, Object> uploadPart(Part file) {
>         // 获取提交文件名称
>         String fileName = file.getSubmittedFileName();
>         try {
>             // 写入文件
>             file.write(fileName);
>         } catch (Exception e) {
>             e.printStackTrace();
>             return dealResultMap(false, "上传失败");
>         } 
>         return dealResultMap(true, "上传成功");
>     }
>     
>     // 处理上传文件结果
>     private Map<String, Object> dealResultMap(boolean success, String msg) {
>         Map<String, Object> result = new HashMap<String, Object>();
>         result.put("success", success);
>         result.put("msg", msg);
>         return result;
>     }
> }
> ```
>
> 

