> 在无注解下获取参数：在没有注解的情况下，spring mvc也可以获取参数， 且参数允许为空，唯一要求是参数名称和http请求的参数名称保持一致.
>
> ```java
> /**
> 	 * 在无注解下获取参数，要求参数名称和HTTP请求参数名称一致.
> 	 * 
> 	 * @param intVal
> 	 *            -- 整数
> 	 * @param longVal
> 	 *            -- 长整形
> 	 * @param str
> 	 *            --字符串
> 	 * @return 响应JSON参数
> 	 */
> 	// HTTP GET请求
> 	@GetMapping("/no/annotation")
> 	@ResponseBody
> 	public Map<String, Object> noAnnotation(Integer intVal, Long longVal, String str) {
> 		Map<String, Object> paramsMap = new HashMap<>();
> 		paramsMap.put("intVal", intVal);
> 		paramsMap.put("longVal", longVal);
> 		paramsMap.put("str", str);
> 		return paramsMap;
> 	}
> /*请台请求:localhost:8080/no/annotation?intVal=10&longVal=20*/
> ```
>
> 

> 使用@RequestParam获取参数：前后台分离的趋势下，前端命名规则和后端不一致，可以使用此注解来确定前后端参数名称的映射关系.
>
> ```java
> /**
> 	 * 通过注解@RequestParam获取参数
> 	 * 
> 	 * @param intVal
> 	 *            -- 整数
> 	 * @param longVal
> 	 *            -- 长整形
> 	 * @param str
> 	 *            --字符串
> 	 * @return 响应JSON数据集
> 	 */
> 	@GetMapping("/annotation")
> 	@ResponseBody
> 	public Map<String, Object> requestParam(@RequestParam("int_val") Integer intVal,
> 	@RequestParam(value = "long_val",required = false /*允许该参数为空*/) Long longVal,   		@RequestParam("str_val") String strVal) {
> 		Map<String, Object> paramsMap = new HashMap<>();
> 		paramsMap.put("intVal", intVal);
> 		paramsMap.put("longVal", longVal);
> 		paramsMap.put("strVal", strVal);
> 		return paramsMap;
> 	}
> /*请台请求:localhost:8080/annotation?int_val=10&long_val=20*/
> ```
>
> 

>传递数组：后台定义数组类型，前台传递参数以逗号分隔
>
>```java
>@GetMapping("/requestArray")
>@ResponseBody
>public Map<String, Object> requestArray(int[] intArr, Long[] longArr, String[] strArr) {
>		Map<String, Object> paramsMap = new HashMap<>();
>		paramsMap.put("intArr", intArr);
>		paramsMap.put("longArr", longArr);
>		paramsMap.put("strArr", strArr);
>		return paramsMap;
>}
>/*请台请求:localhost:8080/requestArray?intArr=10,20,30&longArr=20,30,40&strArr=123,345*/
>```
>
>

> 传递JSON：前后端分离的趋势下，使用json已经十分普遍，通常请求后端的json数据在前台进行展示；有时前端提交复杂的数据到后端，可以将数据转换为JSON数据集，通过HTTP请求提交给后端。
>
> <!--addUser.jsp-->
>
> ```jsp
> <%@ page pageEncoding="UTF-8"%>
> <!DOCTYPE html>
> <html>
> <head>
> <meta charset="UTF-8">
> <title>新增用户用户</title>
> <!-- 加载Query文件-->
> <script src="https://code.jquery.com/jquery-3.2.0.js">
> </script>
> <script type="text/javascript">
> $(document).ready(function() {
>     $("#submit").click(function() {
>         var userName = $("#userName").val();
>         var note = $("#note").val();
>         if ($.trim(userName)=='') {
>             alert("用户名不能为空！");
>             return;
>         }
>         var params = {
>             userName : userName,
>             note : note
>         };
>         $.post({
>             url : "./insert",
>             // 此处需要告知传递参数类型为JSON，不能缺少
>             contentType : "application/json",
>             // 将JSON转化为字符串传递
>             data : JSON.stringify(params),
>             // 成功后的方法
>             success : function(result) {
>                 if (result == null || result.id == null) {
>                     alert("插入失败");
>                     return;
>                 }
>                 alert("插入成功");
>             }
>         });
>     });
> });
> </script>
> </head>
> <body>
>     <div style="margin: 20px 0;"></div>
>     <form id="insertForm">
>         <table>
>             <tr>
>                 <td>用户名称：</td>
>                 <td><input id="userName" name="userName"></td>
>             </tr>
>             <tr>
>                 <td>备注</td>
>                 <td><input id="note" name="note"></td>
>             </tr>
>             <tr>
>                 <td></td>
>                 <td align="right"><input id="submit" type="button" value="提交" /></td>
>             </tr>
>         </table>
>     </form>
> </body>
> ```
>
> <!--UserController.java-->
>
> ```java
> @Controller
> @RequestMapping("/user")
> public class UserController {
> 	// 注入用户服务类
> 	@Autowired
> 	private UserService userService = null;
> 	/**
> 	 * 打开请求页面
> 	 * @return 字符串，指向页面
> 	 */
> 	@GetMapping("/add")
> 	public String add() {
> 		return "/user/add";
> 	}
> 
> 	/**
> 	 * 新增用户
> 	 * @param user  通过@RequestBody注解得到JSON参数
> 	 * @return 回填id后的用户信息
> 	 */
> 	@PostMapping("/insert")
> 	@ResponseBody
> 	public User insert(@RequestBody User user) {
> 		userService.insertUser(user);
> 		return user;
> 	}
> }
> ```
>
> @RequestBody:得到前端传递来的JSON数据，在JSON数据请求体与User类之间的属性应该保持一致，这样就可以通过映射将JSON数据转换为实体类。

> 通过Url路径传递参数，例如:localhost:8080/user/1,这个1就是用户的ID，写入到Url路径当中进行参数传递。后台通过@PathVariable注解来获取传递的参数。
>
> ```java
> 	// {...}代表占位符，还可以配置参数名称
> 	@GetMapping("/{id}")
> 	// 响应为JSON数据集
> 	@ResponseBody
> 	// @PathVariable通过名称获取参数
> 	public User get(@PathVariable("id") Long id) {
> 		return userService.getUser(id);
> 	}
> /*请台请求:localhost:8080/user/1*/
> ```
>
> 

> 获取格式化参数
>
> @NumberFormat：格式化数字
> @DateTimeFormat：格式化日期
>
> <!--前台格式化代码-->
>
> ```jsp
> <%@ page pageEncoding="UTF-8"%>
> <!DOCTYPE html>
> <html>
> <head>
> <meta charset="UTF-8">
> <title>格式化</title>
> </head>
> <body>
> 	<form action="./commit" method="post">
> 		<table>
> 			<tr>
> 				<td>日期（yyyy-MM-dd）</td>
> 				<td><input type="text" name="date" value="2017-08-08" /></td>
> 			</tr>
> 			<tr>
> 				<td>金额（#,###.##）</td>
> 				<td><input type="text" name="number" value="1,234,567.89" /></td>
> 			</tr>
> 			<tr>
> 				<td colspan="2" align="right"><input type="submit" value="提交" />
> 				</td>
> 			</tr>
> 		</table>
> 	</form>
> </body>
> </html>
> ```
>
> <!--后台处理代码-->
>
> ```java
> 	// 获取提交参数
> 	@PostMapping("/format/commit")
> 	@ResponseBody
> 	public Map<String, Object> format(
> 			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
> 			@NumberFormat(pattern = "#,###.##") Double number) {
> 		Map<String, Object> dataMap = new HashMap<>();
> 		dataMap.put("date", date);
> 		dataMap.put("number", number);
> 		return dataMap;
> 	}
> ```
>
>   





