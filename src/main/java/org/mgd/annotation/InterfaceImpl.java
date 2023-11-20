package org.mgd.annotation;

public class InterfaceImpl implements InterFace{
    public static void main(String[] args) {
        InterfaceImpl anInterface = new InterfaceImpl();
        InheritedPeo annotation = anInterface.getClass().getAnnotation(InheritedPeo.class);
        System.out.println(annotation != null);
        anInterface.work();
        InterFace.test();
        System.out.println(InterFace.arr);
    }


}
