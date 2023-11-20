package org.mgd.aop;

import org.mgd.annotation.InterfaceImpl;
import org.mgd.aop.jdk.*;

import java.lang.reflect.*;

public class ApplicationAop{
    public void hello(){
        System.out.println("say hello");
    }
    public static void main(String[] args) throws Exception {
        People people = new PeopleImpl();
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);
                /**
                 *  通过方法获取到自定义注解为空
                 *  因为当前方法是基于jdk动态代理由接口生成的，而接口上的自定义注解不具备继承性
                 */
                System.out.println(annotation!=null);
                Object invoke = method.invoke(people, args);
                System.out.println("after");
                return invoke;
            }
        };
        People proxyPeople = (People) Proxy.newProxyInstance(invocationHandler.getClass().getClassLoader(), people.getClass().getInterfaces(), invocationHandler);
        proxyPeople.learn(33);
    }

    public static void reflect() throws Exception {
        People people = new PeopleImpl();
        People myProxy=(People)MyProxy.newProxyInstance(MyInvocationHandler.class,People.class,new MyInvocationHandler(){
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
                System.out.println("before");
                Object invoke = method.invoke(proxy, args);
                System.out.println("after");
                return "hello";
            }
        });
        myProxy.work();
    }

    public static void byJava() throws Exception {
        Class<PeopleImpl> peopleClass = PeopleImpl.class;
        ClassLoader classLoader = peopleClass.getClassLoader();
        Constructor<PeopleImpl> declaredConstructor = PeopleImpl.class.getDeclaredConstructor();
        People people1 = declaredConstructor.newInstance();
        People people = new PeopleImpl();
        MyInvocationHandler myInvocationHandler = new MyInvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
                System.out.println("before");
                Object invoke = method.invoke(people, args);
                System.out.println("after");
                return invoke;
            }
        };
        People peopleProxy = new PeopleProxy(myInvocationHandler);
        peopleProxy.work();
        peopleProxy.learn(99);
    }

    public void test(){
        InterfaceImpl anInterface = new InterfaceImpl();
    }
}
