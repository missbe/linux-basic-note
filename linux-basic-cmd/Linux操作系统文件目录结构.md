## Linux系统文件目录结构

**rootfs** :根文件系统

**FHS：**Linux文件层级标准

**/boot：**系统启动相关的文件，如内核，initrd，grub（bootloader加载器）

**/dev：**设备文件，块设备（随机访问，数据块）字符设备（线性访问）

**/etc：**配置文件，纯文本文件

**/home：**用户的家目录，默认为/home/username

**/root:** 系统管理员家目录

**/lib：** 库文件和内核模块文件（/lib/modules）

**/var：** 可变化的文件，随着系统运行文件越来越多-越来越大

**/usr：** （shared read-only）

+ /user/bin 系统启动后保证正常运行依赖的库
+ /usr/sbin
+ /usr/lib

  + /usr/local  现在第三方安装软件目录

    + /usr/local/bin

    + /usr/local/sbin
    + /usr/lib

**/bin：** 可执行文件，用户命令

**/sbin：** 可执行文件，管理命令

**/opt：** 可选目录，早期用于第三方软件

**/tmp：** 临时文件，自动清除

**/proc：** 空的，伪文件系统，内核映射文件

**/sys：** 伪文件系统，跟硬件设备相关的属性文件

**/media：** 挂载点目录，移动设备，关联后进行访问

**/mnt：** 挂载点目录，某个设备和目录进行关联后进行访问

### 命名规则 ###

1. 长度不能超过255个字符
2. 不能使用/当文件名
3. 严格区分大小写

### Linux系统功能

+ 文件管理
  + 创建文件
  + 编辑文件
  + 文件权限管理
+ 目录管理
  + 创建目录
  + 目录权限
+ 运行程序
  + 安装程序
  + 配置管理
+ 设备管理
+ 软件管理
+ 进程管理
+ 网络管理

### 目录管理

 + mkdir： /root/x/y/z创建目录z
    + -v（verbose)：执行详细过程
    + -p：自动创建父目录
    + mkdir -pv /mnt/test/{x/m,y} 命令行展开，逗号分隔开创建两个目录
    + mkdir -pv /mnt/test2/{a,b}_{d,c}
+ rmdir（remove directory)：用于删除空目录 （-p递归删除空目录）
 + ls pwd cd tree命令操作目录

### 文件的创建和删除

 + touch filename 创建文件 touch修改时间戳默认文件不存在自动创建
+ file filename 创建文件
+ stat filename 查看文件属性状态
+ rm -i (删除提示) filename  
  + -f（强制删除）
  + -r  (递归删除)
+ mv (move)：mv SRC DEST 
  + -f (force)：强制进行覆盖
  + -i (interactive)：执行命令时可以交互
+ cp (copy)：默认只能复制文件，不能复制目录
  + 一个文件到一个文件
  + 多个文件到一个目录，最后一个参数是文件目的地，前面都是源
  + -r (recursive)：递归复制文件及目录到指定位置
  + -i (interactive)：复制过程有问题会提示
  + -p (perserve) :保留文件原有属性
  + -a (archive)：保持链接属性，复制链接而不是文件，归档复制，常用于备份
+ install ：也可以用来复制文件
  + -d (directory)：用于创建目录