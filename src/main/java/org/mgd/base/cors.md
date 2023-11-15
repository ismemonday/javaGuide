[回到主目录](/README.md)

# 记录一次跨域的问题

```text
遇到跨域问题：前后端分离，客户端发送一个post请求到前端
- 浏览器检查 Origin和Host是否相同，如果相同不存在跨域，不同的话浏览器会自动发送一个OPTIONS请求并携带空body
- 浏览器检查OPTINOS响应状态，如果响应成功并且Response Headers中指定了Access-Control-Allow-Origin的值包含当前Origin 并且Access-Control-Allow-Headers包含请求的Content-Type 则会继续发送post请求
```

```text
headers.set("Access-Control-Allow-Origin", "*"); //必选
headers.set("Access-Control-Allow-Headers", "*"); //必选
```



