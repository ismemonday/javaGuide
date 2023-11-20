package org.mgd.aop.myAop;



import jdk.internal.misc.Unsafe;

import java.lang.reflect.*;
import java.util.function.BiFunction;

public class $Proxy<T> {
    private MyInvocation invocation;
    private static Unsafe UNSAFE;

    public static void main(String[] args) throws Exception {
        $Proxy<People> p1 = new $Proxy();
        People pProxy= (People) p1.getProxy((m, objects)->{
            m.invoke(new People() {
                @Override
                public void doWork() {
                    System.out.println("doWork");
                }
            }, objects);
            System.out.println("after");
        },People.class);
        System.out.println(pProxy.getClass().getName());
        pProxy.doWork();
    }


    private Object getProxy(MyInvocation invocate, Class<T> peopleClass) throws Exception {
        this.invocation=invocate;
        byte[] proxyFile=new byte[1024];
        Object o1 = Proxy.newProxyInstance(this.getClass().getClassLoader(), People.class.getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return null;
            }
        });
        Class<?> clazz = UNSAFE.defineClass("org.mgd.aop.myAop.$Proxy", proxyFile, 0, proxyFile.length, this.getClass().getClassLoader(), null);
        Constructor<?> constructor = clazz.getConstructor(MyInvocation.class);
        Object o = constructor.newInstance(invocate);
        return o;
    }


    public void doSome(){
        People p = (People) Proxy.newProxyInstance(this.getClass().getClassLoader(), People.class.getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(proxy, args);
            }
        });
        p.doWork();
    }
}
