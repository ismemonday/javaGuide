package org.mgd.annotation;

@InheritedPeo("k")
public interface InterFace {
    String arr="a";

     static void test(){
        System.out.println("test");
    }

    default void work(){
        System.out.println("work");
    }
}
