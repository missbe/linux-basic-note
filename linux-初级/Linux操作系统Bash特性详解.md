## Linux操作系统Bash特性详解

**shell：** sh、chs、ksh、bash

**root、student用户登录:** 程序：进程（每一个用户对应一个进程，在每个进程看来，当前主机上只存在内核和当前进程），进程是程序的副本，进程是程序执行的实例.

***用户工作环境：*** 每个用户的工作环境可以不同，每个用户的工作环境可以自定义设置.

**bash：** 工作特性

1. 命令历史

   ```bash
   	    + history：查看命令行历史
        
        	+ history -c：清空命令行历史
        
        	+ history -d OFFSET [n]：删除指定位置命令
        
        	+ history -w：保存命令历史至文件当中
        
        	+ ！n：执行命令历史中第n条命令
        
        	+ ！-n：执行命令历史中倒数第n条命令
        
        	+ ！字符串：执行命令历史中最近的一次以字符串开头的命令
        
        	+ Esc+.和Alt+.：可以引用前一个命令的最后一个参数
        
        	+ 命令补全：连敲两下Tab键
        
        	+ 路径补全：在输入路径下时路径补全，连敲两次Tab键
   ```

2. 管道、重定名；

3. 命令别名：

   ```bash
   alias CMDALIAS=COMMQAND [option] [arguments]
   在shell中定义的别名仅在当前shell生命周期中生效：仅为当前shell进程
   unalias CMDALIAS
   ```

4. 命令替换 ：把命令中某个子命令替换为其执行结果的进程

   ```bash
   + ${COMMAND} `COMMAND`
   echo "The current directory is $(pwd)
   touch ./file-$(date +%F-%M-%S).txt
   bash支持的引号：
   	+ ``；命令替换
   	+""；弱引用，可以实现变量替换
   	+''；强引用，不可以完成变量替换 
   ```

5. 命令行编辑：

   ```bash
   		+ ctrl+a：跳到命令行首
            + ctrl+e：跳到命令行尾
            + ctrl+u：删除至行首
            + ctrl+k：删除至行尾
            + ctrl+l：实现清屏
            + ctrl+-> <-：加左右箭头实现单词跳跃
   ```

6. 命令行展开：

7. 文件名通配：globbing

   ```bash
   *:任意长度的任意字符
   ?:匹配任意单个字符
   []:匹配指定满园内的任意单个字符-[a-zA-Z]:匹配字母
   [^]:匹配指定范围之外的任意个字符-[^0-9]:数字以外字符
   [:space:]:空白字符
   [:punct:]:标点符号
   [:lower:]:小写字母
   [:alpha:]:所有字母
   [:digit:]:数字序列
   [:alnum:]:字母数字
   #man 7 glob 查看详细说明
   ```

8. 变量：

9. 编程：