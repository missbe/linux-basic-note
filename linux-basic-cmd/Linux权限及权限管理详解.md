## Linux权限及权限管理

### 一、Linux目录权限详解

### 权限：

r（4）、w（2）、x（1）

u（属主）、g（属组）、o（其它用户）

### 文件：

+ r：可读
+ w：可写
+ x：可执行（eXacutable）

### 目录：

+ r：可以使用使用ls列出所有文件
+ w：可以在目录中创建文件
+ x：可以用cd命令切换进入此目录，也可以用ls -l查看内部文件详细信息

### 实例：

+ 7 111 rwx：可读可写可执行
+ 660 110110000 rw-rw----

用户：UID，/etc/passwd

用户组：GID，/etc/group

影子口令：

+ 用户：/etc/shadow
+ 用户组：/etc/gshadow

用户类别： 

+ 管理员：0

+ 普通用户：

  + 系统用户：1-499：运行服务程序

  + 一般用户：500-60000：登录系统，获取服务

用户组类别：

+ 管理员组：
+ 普通组：
  + 系统组：
  + 一般组：
+ 用户组类别
  + 基本组：用户的默认组
  + 私有组：创建用户时，未指定用户组，系统自动创建一个与用户同名的组
  + 附加组，额外组：默认组以外的其它组

### 格式：

**/etc/passwd**

+ account:帐户
+ password:密码
+ UID:用户ID
+ GID:用户组ID
+ Home Dir：用户家目录
+ Shell：用户默认shell

**/etc/shadow**

+ account：账户名
+ encrypted password:加密的密码

### 加密方法

+ 对称加密：加密解密使用同一对密码
+ 公钥加密：成对出现，公钥加密，私钥解密，反之亦然
+ 单向加密，散列加密：提取数据特征码。单向加密，数据校验
  + 雪崩效应
  + 定长输出
    + MD5：Message Digest 128
    + SHA1：Secure Hash Algorithm,160

### 二、权限命令

+ **chown:** 改变文件属主（管理员）

  ```bash
  chown USERNAME filename
  -R:修改目录及其内部文件属主（递归修改）
  # 属主属组同时更改
  --reference=/path/to/somefile file,..... #设定file等文件属性和somefile属性一样，参考文件
  chown USERNAME:GRPNAME file,.....
  chown USERNAME.GRPNAME file,.....
  ```

+ **chgrp:** 改变文件属组

  ``` bash
  chgrp GRPNAME file
  -R:递归修改
  --reference=/path/to/somefile file,.....
  ```

+ **chmod:** 修改文件的权限

  ```bash
  #修改三类用户的权限
  chmod MODE file,.....
  -R:递归更改
  --reference=/path/to/somefile file,..... #参考somefile权限更改到file权限里
  chmod 750 file
  #修改某类用户的某些类用户权限
  u,g,o,a(all)
  chmod 用户类别=MODE file,....
  chmod g=rwx file
  #修改某类用户的某位某些权限
  chmod 用户类别 +|- rwx file
  ```

+ **umask:** 创建文件或目录时默认权限依据

  ```bash
  # root:002
  # user:022
  # 文件：666-umask(文件默认不具有执行权限，算出结果有执行权限，则将其权限加1)
  # 目录：777-umask
  ```

### bash的配置文件

***全局配置：*** /etc/profile, /etc/profile.d/*.sh, /etc/bashrc

***个人配置：*** ~/.bash_profile, ~/.bashrc

```bash
# profile类的文件
	+ 设定环境变量
	+ 运行命令或脚本	
# bashsrc类的文件
	+ 设定本地变量
	+ 定义命令别名
```

*** 站在用户角度，shell的类型***

+ 登录式shell
  + 正常终端登录
  + su - username
  + su -l username
+ 非登录式shell
  + su username
  + 图形终端打开命令窗口
  + 自动执行shell脚本

***登录式shell如何读取配置文件：*** 

**/etc/profile --> /etc/profile.d/* *.sh --> ~/.bash_profile --> ~/.bashrc --> /etc/bashrc** 

***非登录式shell如何读取配置文件：***

**~/.bashrc --> /etc/bashrc --> /etc/profile.d/ *.sh** 