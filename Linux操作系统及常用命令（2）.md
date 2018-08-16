## Linux操作系统常用命令 ##

```
# date 时间管理
Linux:rtx实时
ntp（时间服务器）从服务器上同步时间
硬件时间：hwclock查看
系统时间：date查看
功能：查看修改系统时间命令
```

***获得命令使用帮助***

 + 内部命令：help command
+ 外部命令：command --help（长选项）
+ 命令手册（manual）：man command（公用）

***man章节概念***

 	1. 用户命令(/bin,/usr/bin,/usr/local/bin)
	2. 系统调用
	3. 库调用
	4. 特殊文件（设备文件dev）
	5. 文件格式（配置文件的语法）
	6. 游戏相关
	7. 杂项（Miscellaneous)
	8. 管理命令(/sbin,/usr/bin,/usr/local/bin)

```
# what is command
功能：这个命令在那个章节，man中章节查看
# type command
功能：这个命令是内部命令还是外部命令
```

### 选项符号 ###

 + <>:必选项
+ []:可选项
+ ....:可以出现多次
+ （）：分组命令

```BUGS
# man command 帮助说明
	NAME；命令名称及功能简要说明
	SYNOPSIS：用法说明，包括可用的选项
	DESCRIPTION：命令功能的详尽说明，可能包括每一个选项的意义
	OPTIONS：说明每一个短期的意义
	FILES：此命令相关的配置文件
	BUGS：出现错误报告给谁
	EXAMPLES：使用示例
	SEE ALSO：另外参照相关文件
翻屏：
	向后翻一屏：SPACE
	向前翻一屏：b
	向后翻一行：ENTER
	向前翻一行：k
查找：
	/KEYWORD 向后
	？KEYWORD
	n 向后找下一个
	N 向前找下一个
	q 退出
```

```
# hwclock -r 查看系统硬件时钟
	-w:设置硬件时间到系统时间
	-s:设置系统时间到硬件时间
```











