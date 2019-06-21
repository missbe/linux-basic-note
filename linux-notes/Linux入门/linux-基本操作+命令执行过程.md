## 一、linux基本操作命令

```bash
# 查看终端提示符
echo $PS1
# 终端颜色命令-写入文件 /etc/profile.d/xx.sh实现持久化
PS1="\[\e[1;32m\][\u@\h \w]\\$\[\e[0m\]"
# 查看主机名
hostname
# 登录提示信息-message of the day
cat /etc/motd 
# linux内部命令查看方式
enable
help
# 判断命令是不是内部命令
type pwd
pwd 是 shell 内嵌

```

```bash
# 查看命令所处路径 
which pwd
# 查看命令所处路径文档配置文件等信息
whereis pwd
[root@server-13 ~]#which pwd
/usr/bin/pwd
[root@server-13 ~]#whereis pwd
pwd: /usr/bin/pwd /usr/share/man/man1/pwd.1.gz
[root@server-13 ~]#
```

## 二、linux命令执行过程

![linux执行命令](image\linux执行内部命令.png)

```bash
# $PATH命令路径环境变量，按路径查找命令
[root@server-13 ~]#echo $PATH
/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/root/bin
```

```bash
[root@server-13 ~]#hash
命中	命令
   2	/usr/bin/whereis
[root@server-13 ~]#type whereis
whereis 已被哈希 (/usr/bin/whereis)

```

![linux执行外部命令](image\linux执行外部命令.png)

> 小结：1、首先查找该命令是不是别名；2、然后查找该命令是不是内部命令，从内存中找；3、最后查找外部命令，从磁盘中查找；