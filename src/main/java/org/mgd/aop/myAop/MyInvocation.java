package org.mgd.aop.myAop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@FunctionalInterface
public interface MyInvocation {
    /**
     * 处理
     */
    void invocate(Method method,Object[] objs) throws InvocationTargetException, IllegalAccessException;

}
