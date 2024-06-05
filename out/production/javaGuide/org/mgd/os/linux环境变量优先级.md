[回到主目录](/README.md)
## 环境变量常用命令
```html
    env //查看所有的环境变量
    export key=value    //给当前shell窗口设置环境变量（仅仅在当前shell中生效）
    set -a key=value    //设置环境变量
    unset key           //清除环境变量
    echo $key           //查看某个环境变量
    set key=value       //设置当前shell变量
 ```

## 在shell中设置环境变量有以下几种方式：
- 直接在命令行中设置：
```html
 可以使用export命令将环境变量直接设置在命令行中，例如：export MY_VAR="Hello World"
 直接在命令行中设置的环境变量只在当前shell会话中有效，当退出该会话后，该变量就会被清除
```
- 在shell配置文件中设置：
```html
可以将环境变量设置在当前用户的shell配置文件中，例如：.bashrc、.bash_profile、.zshrc等。在这些文件中，可以使用export命令将需要设置的环境变量设置在文件中。
在shell配置文件中设置的环境变量会在每次打开shell时自动加载
```
- 在/etc/environment中设置：
```html
可以在系统级别设置环境变量，这些变量会被所有用户和进程共享。需要在/etc/environment文件中按照"KEY=VALUE"的格式设置环境变量。
在/etc/environment中设置的环境变量是全局的，对所有用户和进程都有效。
```
- 在/etc/profile中设置：
```html
也可以在/etc/profile.d/目录下新建sh脚本来设置环境变量，这些脚本会在用户登录时自动执行。
在/etc/profile.d/中设置的环境变量也是全局的，但只有在用户登录时才会加载
```

## 环境变量优先级
```html
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
## 注意事项
```html
 在用户目录中使用sudo 
 其实获取的是root用户的环境环境,又因为/etc/profile中配置的变量仅在用户登录时生效
 故sudo 执行命令，在/etc/profile中配置的环境变量无效
```