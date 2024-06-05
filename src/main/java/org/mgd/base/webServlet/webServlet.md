[回到主目录](/README.md)

## 1.什么是Web容器？

```text
Web容器，也称为Web应用服务器或Servlet容器，是运行Web应用程序的软件环境。它遵循特定的规范，例如Java中的Java Servlet和JavaServer Pages (JSP) 规范，来管理和执行Web组件。Web容器的主要职责包括：
1.加载和管理Web应用：Web容器负责部署和管理Web应用程序，这些应用通常以WAR（Web Application Archive）文件的形式存在。
2.Servlet生命周期管理：当Web容器启动时，它会加载并初始化Servlet类，处理客户端请求，并在必要时销毁Servlet实例。
3.请求处理：容器接收来自客户端（通常是浏览器）的HTTP请求，解析请求，并将请求转发给相应的Servlet。
4.线程安全：Web容器通常为每个请求创建一个新的线程，以处理Servlet的service方法，确保并发请求的安全性。
5.资源管理：容器管理数据库连接池、会话管理、安全性等资源，为Web应用提供服务。
6.部署描述符解析：读取web.xml等部署描述符文件，配置和管理应用的组件和服务。
7.过滤器链：在示例代码中提到的Filter1实现了Filter接口，这表明它是一个HTTP请求过滤器。Web容器会根据web.xml中的配置，按照指定顺序调用这些过滤器，对请求和响应进行预处理或后处理。

简单说它就干两个事情:
1-启动一个端口处理报文，按照特定协议如http,帮你封装好报文和解析报文
2-提供一个业务接口Servlet，让你自己继承它并实现它的业务逻辑，它负责帮你管理servlet的生命周期

常见的Web容器有tomcat,jetty,tomcat,nginx等等
```

## 2.Servlet是什么?

```text
Servlet（Server Applet）是Java Servlet的简称,他是apache公司提供的一套web容器规范，用来开发web服务。
Servlet容器负责管理Servlet的生命周期，程序员负责实现Servlet并实现业务逻辑
```

## 3.Filter是什么，有什么用？

```text
过滤器和过滤器链，他是Servlet的一个扩展，他可以拦截请求，在请求到达Servlet之前，拦截器可以做很多事情，比如：
1.身份验证
2.权限验证
3.日志记录
4.请求参数处理
等等

核心的过滤器链是  ApplicationFilterChain：应用过滤器链
```

```java
public final class ApplicationFilterChain implements FilterChain {
    private void internalDoFilter(ServletRequest request,
                                  ServletResponse response)
            throws IOException, ServletException {

        //1过滤器链首先会遍历所有的过滤器并执行  filter.doFilter(request, response, this);方法
        if (pos < n) {
            ApplicationFilterConfig filterConfig = filters[pos++];
            try {
                Filter filter = filterConfig.getFilter();

                if (request.isAsyncSupported() && "false".equalsIgnoreCase(
                        filterConfig.getFilterDef().getAsyncSupported())) {
                    request.setAttribute(Globals.ASYNC_SUPPORTED_ATTR, Boolean.FALSE);
                }
                if (Globals.IS_SECURITY_ENABLED) {
                    final ServletRequest req = request;
                    final ServletResponse res = response;
                    Principal principal =
                            ((HttpServletRequest) req).getUserPrincipal();

                    Object[] args = new Object[]{req, res, this};
                    SecurityUtil.doAsPrivilege("doFilter", filter, classType, args, principal);
                } else {
                    filter.doFilter(request, response, this);
                }
            } catch (IOException | ServletException | RuntimeException e) {
                throw e;
            } catch (Throwable e) {
                e = ExceptionUtils.unwrapInvocationTargetException(e);
                ExceptionUtils.handleThrowable(e);
                throw new ServletException(sm.getString("filterChain.filter"), e);
            }
            return;
        }

        try {
            if (ApplicationDispatcher.WRAP_SAME_OBJECT) {
                lastServicedRequest.set(request);
                lastServicedResponse.set(response);
            }

            if (request.isAsyncSupported() && !servletSupportsAsync) {
                request.setAttribute(Globals.ASYNC_SUPPORTED_ATTR,
                        Boolean.FALSE);
            }
            // Use potentially wrapped request from this point
            if ((request instanceof HttpServletRequest) &&
                    (response instanceof HttpServletResponse) &&
                    Globals.IS_SECURITY_ENABLED) {
                final ServletRequest req = request;
                final ServletResponse res = response;
                Principal principal =
                        ((HttpServletRequest) req).getUserPrincipal();
                Object[] args = new Object[]{req, res};
                SecurityUtil.doAsPrivilege("service",
                        servlet,
                        classTypeUsedInService,
                        args,
                        principal);
            } else {
                //2.当过滤器链条调用完了之后会调用当前方法去执行业务逻辑
                servlet.service(request, response);
            }
        } catch (IOException | ServletException | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            e = ExceptionUtils.unwrapInvocationTargetException(e);
            ExceptionUtils.handleThrowable(e);
            throw new ServletException(sm.getString("filterChain.servlet"), e);
        } finally {
            if (ApplicationDispatcher.WRAP_SAME_OBJECT) {
                lastServicedRequest.set(null);
                lastServicedResponse.set(null);
            }
        }
    }
}
```

- ## 4.Filter中doFilter之后再设置setHeader无效

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
    /**
     *原因是filterChain递归完后会调用servlet.service(request, response);
     *会直接将结果写入response
     * 并调用processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);
     **/
}
```

## 5.Interceptor是什么，有什么用？

```text
Spring MVC的Interceptor（拦截器）基于AOP思想，用于在Controller方法执行前后插入额外的处理逻辑。以下是Spring MVC拦截器的原理和实现步骤：
原理：
1.配置拦截器：在Spring MVC的配置文件（如web.xml或使用Java配置）中，通过<mvc:interceptors>标签或者@EnableWebMvc注解配合WebMvcConfigurer接口来定义和注册拦截器。
2.实现拦截器：自定义拦截器类，需要实现org.springframework.web.servlet.HandlerInterceptor接口，或者继承HandlerInterceptorAdapter抽象类，覆盖其中的preHandle、postHandle和afterCompletion方法。
3.拦截流程：
    a.当请求到达时，Spring MVC的DispatcherServlet会查找配置的HandlerMapping来确定哪个Controller方法应该处理请求。
    b.确定Controller方法后，DispatcherServlet会调用HandlerAdapter来准备执行Controller方法。
    c.在调用Controller方法之前，DispatcherServlet会按照注册顺序调用每个拦截器的preHandle方法。如果所有拦截器的preHandle都返回true，则继续执行Controller方法。
    d.Controller方法执行完毕后，Spring MVC会调用每个拦截器的postHandle方法，同样是从最后一个调用preHandle的拦截器开始，逆序调用。
    e.最后，当整个请求处理完成，包括视图渲染后，Spring MVC会调用每个拦截器的afterCompletion方法，也是逆序调用
```

### 5.1.实现思路

- @EnableWebMvc注解配合WebMvcConfigurer接口来定义和注册拦截器。
- 需要实现org.springframework.web.servlet.HandlerInterceptor接口，或者继承HandlerInterceptorAdapter抽象类 --

### 5.2拦截流程

  ```text
    1.当请求到达时，Spring MVC的DispatcherServlet会查找配置的HandlerMapping来确定哪个Controller方法应该处理请求。
    2.确定Controller方法后，DispatcherServlet会调用HandlerAdapter来准备执行Controller方法。    
    3.在调用Controller方法之前，DispatcherServlet会按照注册顺序调用每个拦截器的preHandle方法。如果所有拦截器的preHandle都返回true，则继续执行Controller方法。
    4.Controller方法执行完毕后，Spring MVC会调用每个拦截器的postHandle方法，同样是从最后一个调用preHandle的拦截器开始，逆序调用。
    5.最后，当整个请求处理完成，包括视图渲染后，Spring MVC会调用每个拦截器的afterCompletion方法，也是逆序调用。
   ```

```java
/**
 *拦截核心代码
 **/
public class DispatcherServlet extends FrameworkServlet {
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        //1-前置拦截器处理
        if (!mappedHandler.applyPreHandle(processedRequest, response)) {
            return;
        }
        //2-真实业务处理
        mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
        applyDefaultViewName(processedRequest, mv);
        //3-后置处理器处理
        mappedHandler.applyPostHandle(processedRequest, response, mv);
        //4-结果处理
        processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);
        if (asyncManager.isConcurrentHandlingStarted()) {
            // Instead of postHandle and afterCompletion
            if (mappedHandler != null) {
                mappedHandler.applyAfterConcurrentHandlingStarted(processedRequest, response);
            }
        } else {
            // Clean up any resources used by a multipart request.
            if (multipartRequestParsed) {
                cleanupMultipart(processedRequest);
            }
        }
        
    }
}
```

```java
/**
 *实现代码者@EnableWebMvc注解配合WebMvcConfigurer接口来定义和注册拦截器。
 */
@Configuration
public class WebMvcConfig {
    @Bean
    public WebMvcConfig webMvcConfig() {
        return new WebMvcConfig() {
            @Override
            public void addInterceptors() {
                registry.addInterceptor(new MyInterceptor());
            }
        };
    }
}

public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 预处理逻辑，如权限检查
        return true; // 如果返回true，请求将继续处理；返回false，请求将被中断
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // 后处理逻辑，如更新模型数据
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        // 清理逻辑，如关闭数据库连接
    }
}
```

