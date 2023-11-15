package org.mgd.annotation;

@NoInheritedPeo("类上没有Inherited的接口")
@InheritedPeo("类上有Inherited的接口")
public class People {

    @NoInheritedPeo("work方法上没有Inherited的接口")
    @InheritedPeo("work方法上有Inherited的接口")
    public void work(){
        System.out.println("doSomeWork");
    }
    @NoInheritedPeo("learn方法上没有Inherited的接口")
    @InheritedPeo("learn方法上有Inherited的接口")
    public void learn(){
        System.out.println("do some learn");
    }
}
