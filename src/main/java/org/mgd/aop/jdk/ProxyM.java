package org.mgd.aop.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyM<T>  {
    public static void main(String[] args) {
        Object p =  Proxy.newProxyInstance(People.class.getClassLoader(), PeopleImpl.class.getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(new PeopleImpl(), args);
            }
        });
      People pe= (People) p;
      pe.work();
    }

    public <K,V> ProxyM<T> doSome(K k,V V){
        return null;
    }

    void doSomea(int i, int i1){
        ProxyM<Integer> integerProxyM = new ProxyM<>();
        integerProxyM.doSomea(11,3);
    }
}
