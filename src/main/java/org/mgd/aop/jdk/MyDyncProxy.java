package org.mgd.aop.jdk;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyDyncProxy{

    private final MyInvocationHandler invocationHandler;

    public MyDyncProxy(MyInvocationHandler invocationHandler) {
        this.invocationHandler = invocationHandler;
    }

    public Object general(Method method, Object... objects) throws InvocationTargetException, IllegalAccessException {
        return invocationHandler.invoke(this, method, objects);
    }

    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
