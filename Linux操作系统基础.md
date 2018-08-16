## Linux的基本原则

1. 由目的单一的程序组成，组合小程序完成复杂任务
2. 一切皆文件
3. 尽量避免捕获用户接口
4. 配置文件保存为纯文本格式

***GUI接口*** ：Graphic User Interface

	+ Winows
+ X-Window:主机和显示器分离

***CLI接口*** ：Command Line Interface

	+ bash
+ ksh

***命令提示符：***

#：root

＄：普通用户

***命令***

​	命令　格式 参数

  + 选项：

    ​		 短选项：－

​			长选项：--

+ 参数：命令的作用对象

**使用凭证** ：用户认证，身份认证（严格区分大小写）

虚拟终端（terminal）:Ctrl + Alt + F1-6

**GUI**

 + Gnome:C
 + KDE:C++
 + XPace

**CLI**:Shell接口

 + bash
 + csh
 + zsh
 + ksh
 + tcsh

**切换用户** ：su(switch user)

\# su [-l] 用户名

\# password

***密码复杂性规则*** :

 + 四类字符中选择三种；
+ 至少六位；
+ 周期更新密码； 
+ 循环间距大； 