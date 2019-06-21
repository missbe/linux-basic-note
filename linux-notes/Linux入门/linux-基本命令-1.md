## 一、别名

```bash
# alias 短的命令=长的别名 例如：ls --color=auto
alias cdnet="cd /etc/sysconfig/network-scripts/"
```

```bash
![linux命名别名2](image\linux命名别名2.png)# 别名持久化必须写入文件
# 1、修改配置文件
vi .bashrc
# 2、加载配置文件到内存当中
source .bashrc

[root@server-13 ~]#cat .bashrc
# .bashrc

# User specific aliases and functions

alias rm='rm -i'
alias cp='cp -i'
alias mv='mv -i'

# Source global definitions
if [ -f /etc/bashrc ]; then
	. /etc/bashrc
fi
```

## 二、命令格式+别名说明

![linux命名别名](image\linux命名别名.png)

![linux命名别名2](image\linux命名别名2.png)

![linux命令格式1](image\linux命令格式1.png)

## 三、多个命令执行+多行命令

```bash
# 多个命令一起执行-例如消耗时间的命令一起执行
[root@server-13 /etc/sysconfig/network-scripts]#ls;pwd;hostname
ifcfg-ens192  ifdown-eth   ifdown-post    ifdown-Team      ifup-aliases  ifup-ipv6   ifup-post    ifup-Team      init.ipv6-global
ifcfg-lo      ifdown-ippp  ifdown-ppp     ifdown-TeamPort  ifup-bnep     ifup-isdn   ifup-ppp     ifup-TeamPort  network-functions
ifdown        ifdown-ipv6  ifdown-routes  ifdown-tunnel    ifup-eth      ifup-plip   ifup-routes  ifup-tunnel    network-functions-ipv6
ifdown-bnep   ifdown-isdn  ifdown-sit     ifup             ifup-ippp     ifup-plusb  ifup-sit     ifup-wireless
/etc/sysconfig/network-scripts
server-13
# 一条命令多行完成
[root@server-13 /etc/sysconfig/network-scripts]#cd /etc/sysconfig/ \
> network-scripts
[root@server-13 /etc/sysconfig]#

```

## 四、Linux命令小工具

![linux日期和时间](image\linux日期和时间.png)

```bash
![简单命令screen](image\简单命令screen.png)![简单命令screen](image\简单命令screen.png)# 列出时区
[root@server-13 /etc/sysconfig]#timedatectl list-timezones
Africa/Abidjan
Africa/Accra
Africa/Addis_Ababa
# cal 日历
[root@server-13 /etc/sysconfig]#cal --help

用法：
 cal [选项] [[[日] 月] 年]

选项：
 -1, --one        只显示当前月份(默认)
 -3, --three      显示上个月、当月和下个月
 -s, --sunday     周日作为一周第一天
 -m, --monday     周一用为一周第一天
 -j, --julian     输出儒略日
 -y, --year       输出整年
 -V, --version    显示版本信息并退出
 -h, --help       显示此帮助并退出

[root@server-13 /etc/sysconfig]#cal -y 2019
                               2019                               

        一月                   二月                   三月        
日 一 二 三 四 五 六   日 一 二 三 四 五 六   日 一 二 三 四 五 六
       1  2  3  4  5                   1  2                   1  2
 6  7  8  9 10 11 12    3  4  5  6  7  8  9    3  4  5  6  7  8  9
13 14 15 16 17 18 19   10 11 12 13 14 15 16   10 11 12 13 14 15 16
20 21 22 23 24 25 26   17 18 19 20 21 22 23   17 18 19 20 21 22 23
27 28 29 30 31         24 25 26 27 28         24 25 26 27 28 29 30
                                              31
        四月                   五月                   六月        
日 一 二 三 四 五 六   日 一 二 三 四 五 六   日 一 二 三 四 五 六
    1  2  3  4  5  6             1  2  3  4                      1
 7  8  9 10 11 12 13    5  6  7  8  9 10 11    2  3  4  5  6  7  8
14 15 16 17 18 19 20   12 13 14 15 16 17 18    9 10 11 12 13 14 15
21 22 23 24 25 26 27   19 20 21 22 23 24 25   16 17 18 19 20 21 22
28 29 30               26 27 28 29 30 31      23 24 25 26 27 28 29
                                              30
        七月                   八月                   九月        
日 一 二 三 四 五 六   日 一 二 三 四 五 六   日 一 二 三 四 五 六
    1  2  3  4  5  6                1  2  3    1  2  3  4  5  6  7
 7  8  9 10 11 12 13    4  5  6  7  8  9 10    8  9 10 11 12 13 14
14 15 16 17 18 19 20   11 12 13 14 15 16 17   15 16 17 18 19 20 21
21 22 23 24 25 26 27   18 19 20 21 22 23 24   22 23 24 25 26 27 28
28 29 30 31            25 26 27 28 29 30 31   29 30

        十月                  十一月                 十二月       
日 一 二 三 四 五 六   日 一 二 三 四 五 六   日 一 二 三 四 五 六
       1  2  3  4  5                   1  2    1  2  3  4  5  6  7
 6  7  8  9 10 11 12    3  4  5  6  7  8  9    8  9 10 11 12 13 14
13 14 15 16 17 18 19   10 11 12 13 14 15 16   15 16 17 18 19 20 21
20 21 22 23 24 25 26   17 18 19 20 21 22 23   22 23 24 25 26 27 28
27 28 29 30 31         24 25 26 27 28 29 30   29 30 31

```

![简单命令关机](image\简单命令关机.png)

![简单命令screen](image\简单命令screen.png)



```bash
# 查看linux信息
[root@server-13 /etc/sysconfig]#uname -a
Linux server-13 3.10.0-957.12.2.el7.x86_64 #1 SMP Tue May 14 21:24:32 UTC 2019 x86_64 x86_64 x86_64 GNU/Linux
[root@server-13 /etc/sysconfig]#uname --help
用法：uname [选项]...
输出一组系统信息。如果不跟随选项，则视为只附加-s 选项。

  -a, --all			以如下次序输出所有信息。其中若-p 和
				-i 的探测结果不可知则被省略：
  -s, --kernel-name		输出内核名称
  -n, --nodename		输出网络节点上的主机名
  -r, --kernel-release		输出内核发行号
  -v, --kernel-version		输出内核版本
  -m, --machine		输出主机的硬件架构名称
  -p, --processor		输出处理器类型或"unknown"
  -i, --hardware-platform	输出硬件平台或"unknown"
  -o, --operating-system	输出操作系统名称
      --help		显示此帮助信息并退出
      --version		显示版本信息并退出
   
# 查看cpu信息
[root@server-13 /etc/sysconfig]#lscpu
Architecture:          x86_64
CPU op-mode(s):        32-bit, 64-bit
Byte Order:            Little Endian
CPU(s):                4
On-line CPU(s) list:   0-3
Thread(s) per core:    1
Core(s) per socket:    1
座：                 4
NUMA 节点：         1
厂商 ID：           GenuineIntel
CPU 系列：          6
型号：              45
型号名称：        Intel(R) Xeon(R) CPU E5-2430 0 @ 2.20GHz
步进：              7
CPU MHz：             2200.000
BogoMIPS：            4400.00
超管理器厂商：  VMware
虚拟化类型：     完全
L1d 缓存：          32K
L1i 缓存：          32K
L2 缓存：           256K
L3 缓存：           15360K
NUMA 节点0 CPU：    0-3
Flags:                 fpu vme de pse tsc msr pae mce cx8 apic sep mtrr pge mca cmov pat pse36 clflush dts mmx fxsr sse sse2 ss syscall nx rdtscp lm constant_tsc arch_perfmon pebs bts nopl xtopology tsc_reliable nonstop_tsc eagerfpu pni pclmulqdq ssse3 cx16 pcid sse4_1 sse4_2 x2apic popcnt tsc_deadline_timer aes xsave avx hypervisor lahf_lm tsc_adjust arat
# 查看当前运行级别
[root@server-13 /etc/sysconfig]#runlevel
N 3
# 查看centos版本
[root@server-13 /etc/sysconfig]#cat /etc/centos-release
CentOS Linux release 7.6.1810 (Core) 



```



