package org.mgd.aop.jdk;

import java.io.Serializable;


public class MyProxy implements Serializable {


    public static Object newProxyInstance(Class<MyInvocationHandler> myInvocationHandlerClass, Class<People> peopleClass, MyInvocationHandler myInvocationHandler) throws Exception {
//        //根据接口People生成字节码文件
//        ClassLoader classLoader = myInvocationHandlerClass.getClassLoader();
//        //加载字节码文件
//        Class<?> proxyClass = classLoader.loadClass("ffff");
//        //创建对象返回
//        Constructor<?> constructor1 = proxyClass.getConstructor(null);
//         constructor1.newInstance();
//        Method method = PeopleProxy.class.getMethod("work", null);
//        method.setAccessible(true);
//        method=null;
//        return null;
        MyDyncProxy myDyncProxy = new MyDyncProxy(myInvocationHandler);
        Class<? extends MyDyncProxy> aClass = myDyncProxy.getClass();
        return myDyncProxy;
    }
}
