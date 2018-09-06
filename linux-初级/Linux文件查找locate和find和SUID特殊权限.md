### Linux文件查找命令

+ ***locate:非实时，模糊匹配，查找是根据全系统文件数据库进行的***

+ ***find 查找路径  查找标准 查找到以后的处理操作****

  + 查找路径：默认当前目录

  + 查找标准：查找所有文件

    ```bash
    # find匹配标准
    -name filename:对文件名精确匹配
    # 模糊匹配
    -iname filename:对文件名查找不区分大小写
    -user username：根据用户名查找
    -uid UID：根据UID查找
    -gid GID：根据GID查找
    -nouser:查找没有属主的文件
    -nogroup:查找没有属组的文件
    -type  f/d/c/b/l/p/s --普通文件、目录、字符、块、链接、管道
    -size [+/-] # k/M/G ---根据大小查找
    # 组合条件 find -not -type d
    -a:与
    -o:或
    -not:非
    # 根据时间查找 +至少几天 -到现在为止几天内
    -mtime [+/-]#:修改时间
    -ctime [+/-]#:改变时间
    -atime [+/-]#:访问时间
    # 根据权限查找
    -perm -mode:精确匹配权限，每位相同
    -perm /mode:模糊匹配权限，存在相同
    ```

  + 处理操作：默认显示屏幕上

    ```bash
    -print:打印到时屏幕
    -ls:类似ls -l形式显示
    -ok COMMAND {}\; --每一次操作需要用户确认
    -exec COMMAND {}\; --不需要确认
    ```

  ### 特殊权限

  + SUID：运行某程序时，相应进程的属主是程序文件自身的属主，而不是启运者

    ```bash
    chmode u+s FILE
    chmod  u-s FILE
    # 如果FILE本身就有执行权限，则SUID显示为s，否则显示为S
    ```

  + SGID：运行程序时，相应进程的属组是程序文件自身属组，而不是启动者本身的属组

    ```bash
    chmod g+s FILE
    chmod g-s FILE
    # # 如果FILE本身就有执行权限，则SUID显示为s，否则显示为S
    ```

  + Sticky:在一公共目录，每个人都可以创建文件、删除自己的文件，但不能删除别人的文件

    ```bash
    chmod o+t DIR
    chmod o-t DIR
    # chmod 5755 DIR 第一位5表示suid+sticky
    ```

