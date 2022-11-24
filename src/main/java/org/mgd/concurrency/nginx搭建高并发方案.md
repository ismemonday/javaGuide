[回到主目录](/README.md)
## 静态资源
```shell
server{
  listen 80; //监听80端口,一个server有切只有一个listen
  servername statistic_resource; //服务器名称
  default_type application/json; //响应类型默认(text/plain)
  keepalive_timeout  65; //长连接时间s,超过65s没有请求就自动断开
  add_header Access-Control-Allow-Origin *; //添加请求头,此可用于解决跨域
  charset utf-8; //配置字符编码 
  location / {  
  //静态资源配置 如http://localhost:80/api/test1--->nginx就将文件/home/dt/api/test1返回
  //请求 http://localhost:80/api/test1--返回/home/dt/api/test1/(index.html或home.html或aaa.html)
    root /home/dt  //绝对路径或者相对路径都可以
    index index.html home.html aaa.html 如http://localhost:80--->nginx就将文件/home/dt/index.html返回
  }
  
  location /good/ {  
    root /home/dt  //绝对路径或者相对路径都可以,访问http://localhost:80/good/aab-->访问home/dt/good/aab
  }
}
```
## 反向代理
```shell
server{
  listen 80; //监听80端口,一个server有切只有一个listen
  servername statistic_resource; //服务器名称
  location /good1 {  
   proxy_pass http://localhost:80/api     //访问localhost:80/good1/ab==访问http://localhost:80/apiab
  }
  
    location /good2 {  
   proxy_pass http://localhost:80/api/  //访问localhost:80/good1/ab==访问http://localhost:80/api/ab
  }
}
```

## 负载均衡
```shell
worker_processes  1;
events {
    worker_connections  1024;
}

http {
    include       mime.types;
    default_type  application/octet-stream; 
    keepalive_timeout  65;
    upstream proxy_host{
#   server localhost:80/a ; #只允许配置ip:端口，不可以加路径
	server localhost:80 ;
	server localhost:81 ;
     }
    server {
        listen       80;
        server_name  s1;
        location /a {
          root C:\Users\maoguidong\Desktop\front_models;
          index a.txt ;
        }
       location /proxy {
         	proxy_pass http://proxy_host/;
        }
    }
    server {
        listen       81;
        server_name  s2;
        location /a {
          root C:\Users\maoguidong\Desktop\front_models;
          index b.txt ;
        }
    }
}
//访问localhost/proxy/a  == http://localhost:80/a 或者 http://localhost:81/a
```
