```java
// 显示用户
// 参数user直接从数据模型RedirectAttributes对象中取出
@RequestMapping("/showUser")
public String showUser(User user, Model model) {
    System.out.println(user.getId());
    return "data/user";
}

// 使用字符串指定跳转
@RequestMapping("/redirect1")
public String redirect1(String userName, String note, RedirectAttributes ra) {
    User user = new User();
    user.setNote(note);
    user.setUserName(userName);
    userService.insertUser(user);
    // 保存需要传递给重定向的对象
    ra.addFlashAttribute("user", user);
    return "redirect:/user/showUser";
}
// 使用模型和视图指定跳转
@RequestMapping("/redirect2")
public ModelAndView redirect2(String userName, String note,
	        RedirectAttributes ra) {
     User user = new User();
	 user.setNote(note);
	 user.setUserName(userName);
	 userService.insertUser(user);
	 // 保存需要传递给重定向的对象
	 ra.addFlashAttribute("user", user);
	 ModelAndView mv = new ModelAndView();
	 mv.setViewName("redirect:/user/showUser");
	 return mv;
}
```

NOTE：重定向是通过session域实现的，保存重定域的数据到session域中，重定向再从session域中获取保存的数据，获取成功后再从session域中删除；

![](E:\Typora\study-basic-note\spring-boot-note\img\重定向.png)

