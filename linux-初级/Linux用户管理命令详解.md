## Linux用户管理命令详解

+ **/etc/passwd:**

  用户名：密码：UID：GID：注释：家目录：默认Shell

+ **/etc/group:**

  组名：密码：最近一次修改密码时间：最短使用期限：最长使用时间：警告时间：非活动时间：过期时间

+ ***用户管理命令:***

  **useradd,userdel,usermod,passwd,chsh,chfn,finger,id,chage**

+ **useradd：**添加新用户

  ```bash
  + user -u  1000 user-group
  + -u:UID（用户ID）
  + -g:GID（基本组）
  + -G：GID，.....（额外组，附加组）
  + -c:"COMMENT"
  + -d:/path/to/directory
  + -s:/bin/bash--/etc/shells(指定了当前系统可用的安全shell)
  + -r:添加系统用户
  ```

+ **userdel [option] username：** 删除指定用户

  ```bash
  -r:同时删除用户的家目录
  ```

   + id:查看用户帐户属性信息

     ```bash
     -u:UID
     -g:GID
     -G:所有组ID
     -n:组名
     ```

  + finger:检索用户账号信息

    ```bash
    finger username
    ```

+ **usermod:**修改用户帐号属性

  ```bash
  -u:UID
  -g:GID(基本组)
  -G:(附加组)-会覆盖原来的
  -a -G:保留原来的附加组，加入新的附加组
  -d:指定新的家目录
  -c:注释信息
  -e:指定过期时间，过期了帐号禁用
  -L：锁定帐号，禁用
  -U：解除帐号锁定
  ```

+ **chsh：** 改变使用的shell

+ **chfn：** 改变用户注释信息

+ **passwd:** 用户只能改自己，管理员可以改别个

  ```bash
  --stdin:标准输入
  -l:锁定用户帐号
  -u:解除锁定
  -d:删除用户密码
  ```

+ **pwck:**  检查用户帐号完整性

+ **chage:**

  ```bash
  -d:最近一次修改时间
  -E:过期时间
  -I:非活动时间
  -m:最短使用期限
  -M:最长使用期限
  -W:警告时间
  ```

  ### 组管理：

  + **groupadd:** 

    ```bash
    -g:GID
    -r:添加系统组
    ```

  + **groupmod:**

    ```bash
    -g:GID
    -n:rename
    ```

  + **groupdel:** 删除指定组 man groupdel

  + **gpasswd:** 为组设置密码

  + ***newgrp GRPNAME <---> exit:*** 临时登陆到一个组 