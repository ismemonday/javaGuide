package org.mgd.aop.jdk;

public class PeopleImpl implements People{
    @Override
    public Integer work() {
        System.out.println("people impl do work");
        return 16;
    }

    @Override
    @MyAnnotation("annotation learn")
    public String learn(int a) {
        System.out.println("people learn"+a);
        return "ssss";
    }
}
