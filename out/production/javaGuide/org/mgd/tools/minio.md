[回到主目录](/README.md)
# minio简介
```shell
        minio是一款文件服务器，它有clinet和console两部分组成，其中client提供了文件存储功能，console提供web对文件的可视化操作
```

## 安装minio
[官网下载minio](https://www.minio.org.cn/download.shtml#/windows)
```java
以linux系统为例子
//下载minion
wget   http://dl.minio.org.cn/server/minio/release/linux-amd64/minio  
//为minio配置可执行权限 
chmod +x minio
```
## 启动minio
```java
 //启动minio并指定console端口,此时会有默认账号秘密
 /data/minio server /data/minio/data --console-address :9001

//MinIO server在默认情况下会将所有配置信息存到 ${HOME}/.minio/config.json 文件中
//也可以启动时指定配置文件，配置文件可设置账号密码，它会去/etc/minio/config.json中获取配置信息
 /data/minio server --config-dir /etc/minio /data/minio/data --console-address :9001

//config.json中配置
MINIO_ROOT_USER=admin
MINIO_ROOT_PASSWORD=123
```
## 配置minio开机自启动
```java
[Unit]
Description=minio server.....
After=network.target

[Service]
EnvironmentFile=/data/minio.conf
ExecStart=/data/minio server /data --console-address :8080
ExecReload=/bin/kill -s HUP $MAINPID
ExecStop=/bin/kill -s QUIT $MAINPID

[Install]
WantedBy=default.target


//minio.conf
MINIO_ROOT_USER=admin
MINIO_ROOT_PASSWORD=123
```
## web访问
```java
通过浏览器访问：localhost:8080 账号-密码admin-123就可以访问
```