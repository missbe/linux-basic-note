## Linux管理及IO重定向

### 系统设定

 + 默认输出设备：标准输出-stdout
 +  默认输入设备：标准输入-stdin
 + 标准错误输出：stderr
 + 标准输入：键盘
 + 标准输出、错误输出：显示器

### IO重定向

+ '>':输出重定向-覆盖输出
+ '>>':保留原来内容-追加输出
+ set -C:禁止对存在文件覆盖输出,（>| ）可以强制覆盖
+ set +C:关闭上述功能
+ 2>:重定向错误输出
+ 2>>:追加错误输出
+ &>:重定向标准输入或者标准错误输出
+ '<':输入重定向
+ '<<':Here Document-cat >> out.txt << EOF

### 管道：前一个命令的输出作为后一个命令的输入

***命令1 | 命令2 | 命令3 | .....*** 

'# echo "alias cls=clear"  >>  ~.bashrc