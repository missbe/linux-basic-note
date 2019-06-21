## Linux操作系统常用命令（1）##

### 操作系统库 ###

***dll*** :Dynamic Link Library

***.so*** :shared object

**login:**

​	用户名：用户ID标识

**认证机制：**Authentication

**资源授权：**Authorization

**审计：** Audition（日志）

***prompt***:命令提示符

***命令：***命令 选项 参数

```bash
+ \# command options.... paramter....
```

```bash
# list: ls
选项：
	-l:显示完整信息，长格式，
	  	 cd -文件类型（-：普通文件，d:目录文件，b:块设备文件，c：字符设备文件），
	  	 文件权限：9位3位一组：rwx（读，写，执行），
	 	 文件硬链接的次数，
     	 文件属主（owner），
     	 文件属组（group），
     	 文件大小（size）：默认单位字节，
     	 时间戳（timestamp)：最近一次被修改的时间（访问：access,修改:modify,改变：change,metadata）
    -h（human）:单位换算，人类易识别
    -a（all）:显示以.开头的文件
    	. 表示当前目录
    	.. 表示父目录
    -d（directory）:显示目录自身属性
    -i（index）:index node,inode
    -r:逆序显示文件
    -R（recursive）:递归显示文件
路径：从指定起始点到目的地所经过的位置
功能：列出指定路径下的文件
```

***magic number:*** 魔数

***shebang***:`#!/bin/bash`

***路径：*** 绝对路径从根开始找，相对路径从当前路径开始找（working directory）

```bash
# pwd:Printing Working Directory
功能：打印当前工作目录
```

```bash
# cd:change directory
cd -:在当前目录和前一次所在目录之间来回切换
cd ~用户名：到达指定用户下的家目录（root权限）
cd不加任何参数，回到家目录
功能：切换到指定目录。
```

**命令类型：**

 + 内置命令（shell内置）：内部，内置，内建
+ 外部命令：在文件系统的某个路径下有一个与命令名称相对应的可执行文件

***type：***显示指定属于那种类型

***环境变量：*** 变量是命名的内存空间，保存软件运行工作空间。PATH保存命令执行所在的执行文件路径

```bash
# hash 查看缓存命令
# printenv 打印环境变量清单
```





