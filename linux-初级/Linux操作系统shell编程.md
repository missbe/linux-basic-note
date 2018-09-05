## Linux操作系统shell编程

shell：弱类型语言

### bash变量类型

 + 环境变量:作用域为当前shell及其了进程 
    + --export varname=value
    + 命令行执行中启动的脚本会继承当前shell环境变量
    + 系统自动执行的脚本需要自定义需要的环境变量
+ 局部变量：local varname=value
  + 作用域为当前代码段
+ 本地变量:varname=value
  +  --作用域为整个bash进程 
+ 位置变量：
+ 特殊变量：
+ 程序执行结果
  + 正确执行返回0
  + 错误执行返回1-255 --1, 2, 127系统预留

### 输出重定向

+ '>'
+ '>>'
+ 2>
+ 2>>
+ &>

### 变量命令

```bash
# 撤销变量
unset varname
#查看当前shell中变量
set
#查看shell的环境变量
printenv
env
export
```





















