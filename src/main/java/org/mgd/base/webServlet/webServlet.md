[回到主目录](/README.md)

# 什么是Web容器？
# Servlet是什么
# Filter是什么，有什么用？
```text

```
- ## Filter中doFilter之后再设置setHeader无效 
```java
public class Filter1 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("start");
        HttpServletResponse resp = (HttpServletResponse) response;
        chain.doFilter(request, resp);
        //后面的操作无效
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Method", "*");
        System.out.println("after");
    }
}
```
# Interceptor是什么，有什么用？


