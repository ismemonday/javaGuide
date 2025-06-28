[回到主目录](/README.md)

# 跨域由来

```text
 *跨域* 是浏览器的安全策略限制
浏览器可以直接禁用跨域：  C:\Users\chrome.exe --disable-web-security --user-data-dir=D:\temp\cros
```

# 域的定义

```text
协议、域名、端口号
下面是两个不同的域名
http://localhost:8089 
http://localhost:8888

localhost和127.0.0.1属于相同域
```

# 跨域浏览器具体干了什么

```text
1-浏览器检查 Origin和Host是否相同，如果相同不存在跨域，不同的话浏览器会校验当前请求是‘简单请求’还是‘复杂请求’
2.1'简单请求'-如果当前请求是简单请求，浏览器会直接发送当前请求，并判断response的header中是否包含Access-Control-Allow-Origin和Access-Control-Allow-Methods是否包含当前请求的method和当前域名，如何匹配就允许跨域，反之报CORS错误
2.2‘复杂请求’-如果当前请求是复杂请求，浏览器会发送一个OPTIONS请求，OPTIONS请求的method是OPTIONS，请求头中包含Origin和Access-Control-Request-Method，请求体为空，如果OPTIONS请求成功，OPTIONS请求的判断和简单请求判断一样
3-Options请求不会每次都发送，浏览器有记录
```

## 简单请求和复杂请求
```test
简单请求的定义：
1-请求头只能是 GET POST HEAD
2-请求头中不能包含自定义的header
3-请求体Content-Type只能是 application/x-www-form-urlencoded、multipart/form-data、text/plain
```

## OPTIONS请求优化

```text
浏览器行呢个
1-浏览器为了避免每次发送复杂请求时都要发送一次OPTIONS请求
```

```text
遇到跨域问题：前后端分离，客户端发送一个post请求到前端
- 浏览器检查 Origin和Host是否相同，如果相同不存在跨域，不同的话浏览器会自动发送一个OPTIONS请求并携带空body
- 浏览器检查OPTINOS响应状态，如果响应成功并且Response Headers中指定了Access-Control-Allow-Origin的值包含当前Origin 并且Access-Control-Allow-Headers包含请求的Content-Type 则会继续发送post请求
```

```text
headers.set("Access-Control-Allow-Origin", "*"); //必选
headers.set("Access-Control-Allow-Headers", "*"); //必选
```



