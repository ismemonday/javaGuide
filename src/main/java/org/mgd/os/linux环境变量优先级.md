[回到主目录](/README.md)
# linux环境变量优先级
---
## 环境变量常用命令
```java
    env //查看所有的环境变量
    export key=value    //设置环境变量
    set -a key=value    //设置环境变量
    unset key           //清除环境变量
    echo $key           //查看某个环境变量
    set key=value       //设置当前shell变量
    
```
## 环境变量优先级
```java
    /etc/profile:系统环境配置加载的第一个文件，仅系统加载时或执行source时加载1次
    /etc/environment:系统环境配置加载的第二个文件，仅仅读取用户环境变量前加载1次或执行soruce加载
    ~/.bash_profile:用户环境加载执行的第一个文件，用户登录时仅加载1次或执行source时
    ~/.bashrc:用户环境加载执行的第二个文件，每次shell时加载
    source /etc/profile  //修改后更新配置

    环境变量的优先级规则：
        系统配置中有相同的key,系统加载时以第一个key为准，
        执行source会以当前为准
        用户环境配置了相同的key,以最后加载的为准。
```
