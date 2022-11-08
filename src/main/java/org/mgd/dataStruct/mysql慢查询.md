[回到主目录](/README.md)

## mysql慢查询说明
![](imgs/slow1.png)

- mysql慢查询相关参数
```java
    登录mysql:
    查看慢查询相关参数：show variables like '%query%'
    设置mysql慢查询参数：
        - set global long_query_time=5;
        - set global slow_query_log=ON;
        - set global slow_query_log_file=`D:\tmp\*.log`;
```
- mysql慢查询设置long_query_time无效
```java
    其实设置已经生效，只作用于新的连接，故关闭当前连接新建连接可查看已经修改生成
```
- 系统重启mysql慢查询配置失效
```java
    set global *=*,此类配置只作用于当前用户空间，重启系统或重启mysql配置失效果。
    如果需要重启mysql也生效需要在mysql启动配置文件my.cnf中添加相关配置。
```
- 通过系统配置让mysql慢查询重启也生效
```java
    linux中，在/etc/my.cnf或/etc/mysql/my.cnf中配置
```
![linux中](imgs/slow3.png)

```java
    windows中，在mysql安装目录下的my.ini
```
![windows中](imgs/slow2.png)

- 慢查询测试
```java
    1、开启慢查询
    2、测试慢查询记录 select sleep(5),睡5s
    3、查看慢查询记录条数 show status like '%slow_queries%'
```
![](imgs/slow5.png)