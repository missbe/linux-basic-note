JSR-303验证

><!--ValidatorPojo对象设置校验标准-->
>
>```java
>package com.springboot.chapter10.pojo;
>
>import java.util.Date;
>
>import javax.validation.constraints.DecimalMax;
>import javax.validation.constraints.DecimalMin;
>import javax.validation.constraints.Email;
>import javax.validation.constraints.Future;
>import javax.validation.constraints.Max;
>import javax.validation.constraints.Min;
>import javax.validation.constraints.NotNull;
>import javax.validation.constraints.Size;
>
>import org.hibernate.validator.constraints.Range;
>import org.springframework.format.annotation.DateTimeFormat;
>@Data
>public class ValidatorPojo {
>
>	// 非空判断
>	@NotNull(message = "id不能为空")
>	private Long id;
>
>	@Future(message = "需要一个将来日期") // 只能是将来的日期
>	// @Past //只能去过去的日期
>	@DateTimeFormat(pattern = "yyyy-MM-dd") // 日期格式化转换
>	@NotNull // 不能为空
>	private Date date;
>
>	@NotNull // 不能为空
>	@DecimalMin(value = "0.1") // 最小值0.1元
>	@DecimalMax(value = "10000.00") // 最大值10000元
>	private Double doubleValue = null;
>
>	@Min(value = 1, message = "最小值为1") // 最小值为1
>	@Max(value = 88, message = "最大值为88") // 最大值88
>	@NotNull // 不能为空
>	private Integer integer;
>
>	@Range(min = 1, max = 888, message = "范围为1至888") // 限定范围
>	private Long range;
>
>	// 邮箱验证
>	@Email(message = "邮箱格式错误")
>	private String email;
>
>	@Size(min = 20, max = 30, message = "字符串长度要求20到30之间。")
>	private String size;
>}
>```
>
><!--Controller校验对象--> 
>
>```java
>/***
>	 * 解析验证参数错误
>	 * @param vp —— 需要验证的POJO，使用注解@Valid 表示验证
>	 * @param errors  错误信息，它由Spring MVC通过验证POJO后自动填充
>	 * @return 错误信息Map
>	 */
>	@RequestMapping(value = "/valid/validate")
>	@ResponseBody
>	public Map<String, Object> validate(
>	        @Valid @RequestBody ValidatorPojo vp, Errors errors) {
>	    Map<String, Object> errMap = new HashMap<>();
>	    // 获取错误列表
>	    List<ObjectError> oes = errors.getAllErrors();
>	    for (ObjectError oe : oes) {
>	        String key = null;
>	        String msg = null;
>	        // 字段错误
>	        if (oe instanceof FieldError) {
>	            FieldError fe = (FieldError) oe;
>	            key = fe.getField();// 获取错误验证字段名
>	        } else {
>	            // 非字段错误
>	            key = oe.getObjectName();// 获取验证对象名称
>	        }
>	        // 错误信息
>	        msg = oe.getDefaultMessage();
>	        errMap.put(key, msg);
>	    }
>	    return errMap;
>	}
>```

自定义验证逻辑

><!--实现Validator接口实现自定义验证逻辑-->
>
>```java
>package com.springboot.chapter10.validator;
>
>import org.springframework.util.StringUtils;
>import org.springframework.validation.Errors;
>import org.springframework.validation.Validator;
>
>import com.springboot.chapter10.pojo.User;
>
>/**** imports ****/
>public class UserValidator implements Validator {
>	
>	// 该验证器只是支持User类验证
>	@Override
>	public boolean supports(Class<?> clazz) {
>		return clazz.equals(User.class);
>	}
>
>	// 验证逻辑
>	@Override
>	public void validate(Object target, Errors errors) {
>		// 对象为空
>		if (target == null) {
>			// 直接在参数处报错，这样就不能进入控制器的方法了
>			errors.rejectValue("", null, "用户不能为空");
>			return;
>		}
>		// 强制转换
>		User user = (User) target;
>		// 用户名非空串
>		if (StringUtils.isEmpty(user.getUserName())) {
>			// 增加错误，可以进入控制器方法
>			errors.rejectValue("userName", null, "用户名不能为空");
>		}
>	}
>}
>```
>
>有了这个机制，spring mvc还不会启用它，因为没有绑定给WebDataBinder机制，spring mvc提供一个注解@InitBinder：它的作用是在执行控制器方法前，处理器会先执行标@InitBinder的方法，通过这个可以得到WebDataBinder对象，这个对象有一个setValidator方法，它可以绑定自定义的验证器，就可以在 获取参数后，通过自定义的验证器去验证参数。
>
>```java
>@Controller
>@RequestMapping("/user")
>public class UserController {
>    /**
>	 * 调用控制器前先执行这个方法
>	 * 
>	 * @param binder
>	 */
>	@InitBinder
>	public void initBinder(WebDataBinder binder) {
>		// 绑定验证器
>		binder.setValidator(new UserValidator());
>		// 定义日期参数格式，参数不再需注解@DateTimeFormat，boolean参数表示是否允许为空
>		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false));
>	}
>
>	/**
>	 * 
>	 * @param user
>	 *            -- 用户对象用StringToUserConverter转换
>	 * @param Errors
>	 *            --验证器返回的错误
>	 * @param date
>	 *            -- 因为WebDataBinder已经绑定了格式，所以不再需要注解
>	 * @return 各类数据
>	 */
>	@GetMapping("/validator")
>	@ResponseBody
>	public Map<String, Object> validator(@Valid User user, Errors Errors, Date date) {
>		Map<String, Object> map = new HashMap<>();
>		map.put("user", user);
>		map.put("date", date);
>		// 判断是否存在错误
>		if (Errors.hasErrors()) {
>			// 获取全部错误
>			List<ObjectError> oes = Errors.getAllErrors();
>			for (ObjectError oe : oes) {
>				// 判定是否字段错误
>				if (oe instanceof FieldError) {
>					// 字段错误
>					FieldError fe = (FieldError) oe;
>					map.put(fe.getField(), fe.getDefaultMessage());
>				} else {
>					// 对象错误
>					map.put(oe.getObjectName(), oe.getDefaultMessage());
>				}
>			}
>		}
>		return map;
>	}
>}
>```
>
>



