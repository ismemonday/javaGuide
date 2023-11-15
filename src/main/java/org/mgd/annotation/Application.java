package org.mgd.annotation;


public class Application {
    public static void main(String[] args) throws NoSuchMethodException {
        PeopleImpl peopleImpl = new PeopleImpl();
        InheritedPeo classHasInheritedAnnotation = peopleImpl.getClass().getAnnotation(InheritedPeo.class);
        NoInheritedPeo classNoInheritedAnnotation = peopleImpl.getClass().getAnnotation(NoInheritedPeo.class);

        System.out.println(classHasInheritedAnnotation != null); //自定义注解包含@Inherited，子类获取父类的自定义注解  true
        System.out.println(classNoInheritedAnnotation != null); // 自定义注解没有@Inherited，子类获取父类的自定义注解  false

        InheritedPeo workNoOverwriteInherited = peopleImpl.getClass().getMethod("work").getAnnotation(InheritedPeo.class);
        NoInheritedPeo workNoOverwriteNoInherited = peopleImpl.getClass().getMethod("work").getAnnotation(NoInheritedPeo.class);
        InheritedPeo learnOverwriteInherited = peopleImpl.getClass().getMethod("learn").getAnnotation(InheritedPeo.class);
        NoInheritedPeo learnOverwriteNoInherited = peopleImpl.getClass().getMethod("learn").getAnnotation(NoInheritedPeo.class);
        System.out.println(workNoOverwriteInherited != null);   //自定义注解包含@Inherited，子类获取父类的方法上的注解，此方法在子类未重写     true
        System.out.println(workNoOverwriteNoInherited != null); //自定义注解没有@Inherited，子类获取父类的方法上的注解，此方法在子类未重写     true
        System.out.println(learnOverwriteInherited != null);    //自定义注解包含@Inherited，子类获取父类的方法上的注解，此方法在子类重写      false
        System.out.println(learnOverwriteNoInherited != null);  //自定义注解没有@Inherited，子类获取父类的方法上的注解，此方法在子类重写      false
    }

}
