package org.mgd.aop.jdk;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;


public class PeopleProxy implements People{
    private final MyInvocationHandler myInvocationHandler;


    public PeopleProxy(MyInvocationHandler myInvocationHandler) {
        this.myInvocationHandler = myInvocationHandler;
    }



    @Override
    public Integer work() {
        try {
            Method work = People.class.getMethod("work", null);
           return (Integer) myInvocationHandler.invoke(this,work,null);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String learn(int a) {
        try {
            Method learn = People.class.getMethod("learn", int.class);
            Object[] objects = Arrays.asList(a).toArray();
            return (String) myInvocationHandler.invoke(this,learn,objects);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
