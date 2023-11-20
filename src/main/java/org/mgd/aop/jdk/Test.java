package org.mgd.aop.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.BiFunction;

/**
 * 多重代理
 * 首先给Peopleimpl代理
 * 在给Peopleimpl的代理类代理
 */
public class Test {
    public static void main(String[] args) {
        PeopleImpl people = new PeopleImpl();
        people.work();
        People proxy1 = (People) Proxy.newProxyInstance(people.getClass().getClassLoader(), PeopleImpl.class.getInterfaces(),  getInvocationHander(people));
        proxy1.work();
        People proxy2  = (People) Proxy.newProxyInstance(people.getClass().getClassLoader(), proxy1.getClass().getInterfaces(),  getInvocationHander(proxy1));
        proxy2.work();
        People proxy3  = (People) Proxy.newProxyInstance(people.getClass().getClassLoader(), proxy1.getClass().getInterfaces(),  getInvocationHander(proxy2));
        proxy3.work();
    }

    private static InvocationHandler getInvocationHander(People people) {
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("代理啦");
                return method.invoke(people, args);
            }
        };
        return invocationHandler;
    }
}
